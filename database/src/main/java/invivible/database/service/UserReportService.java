package invivible.database.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import invivible.database.models.objects.UserReport;
import invivible.database.repository.UserReportRepository;

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
public class UserReportService {

  private final String USER_REPORT_SEQUENCE = "user_report_sequence";

  private final UserReportRepository userReportRepository;
  private final SequenceGeneratorService sequenceGeneratorService;

  public UserReportService(UserReportRepository userReportRepository, SequenceGeneratorService sequenceGeneratorService) {
    this.userReportRepository = userReportRepository;
    this.sequenceGeneratorService = sequenceGeneratorService;
  }

  public ResponseEntity<UserReport> getUserReport(Long userReportId) {
    Optional<UserReport> byId = userReportRepository.findById(userReportId);
    return byId.map(userReport -> new ResponseEntity<>(userReport, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  public ResponseEntity<Long> postUserReport(UserReport userReport) {
    userReport.setId(sequenceGeneratorService.generateId(USER_REPORT_SEQUENCE));
    userReport.setReportDate(new Date());
    return new ResponseEntity<>(userReportRepository.save(userReport).getId(), HttpStatus.OK);
  }

  public ResponseEntity<List<UserReport>> getAllOpenUserReports() {
    return new ResponseEntity<>(userReportRepository.findUserReportsBySolvedFalse(), HttpStatus.OK);
  }
}
