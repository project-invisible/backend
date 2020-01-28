package invisible.database.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import invisible.database.models.objects.RatingReport;
import invisible.database.repository.RatingReportRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  27.12.2019
 * <p>
 * <p/>
 */
@Service
public class RatingReportService {

  private final String RATING_REPORT_SEQUENCE = "rating_report_sequence";

  private RatingReportRepository ratingReportRepository;
  private SequenceGeneratorService sequenceGeneratorService;

  public RatingReportService(RatingReportRepository ratingReportRepository, SequenceGeneratorService sequenceGeneratorService) {
    this.ratingReportRepository = ratingReportRepository;
    this.sequenceGeneratorService = sequenceGeneratorService;
  }

  public ResponseEntity<Long> postRatingReport(RatingReport ratingReport) {
    ratingReport.setId(sequenceGeneratorService.generateId(RATING_REPORT_SEQUENCE));
    ratingReport.setReportDate(new Date());
    return new ResponseEntity<>(ratingReportRepository.save(ratingReport).getId(), HttpStatus.OK);
  }

  public ResponseEntity<RatingReport> getRatingReport(Long reportId) {
    Optional<RatingReport> byId = ratingReportRepository.findById(reportId);
    return byId.map(ratingReport -> new ResponseEntity<>(ratingReport, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  public ResponseEntity<List<RatingReport>> getAllOpenReports() {
    return new ResponseEntity<>(ratingReportRepository.findRatingReportsBySolvedFalse(), HttpStatus.OK);
  }
}
