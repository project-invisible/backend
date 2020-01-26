package invivible.database.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import invivible.database.models.objects.RatingReport;
import invivible.database.service.RatingReportService;

import java.util.List;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  27.12.2019
 * <p>
 * <p/>
 */
@RestController
@RequestMapping("/ratingReport")
public class RatingReportController {

  private final RatingReportService ratingReportService;

  public RatingReportController(RatingReportService ratingReportService) {
    this.ratingReportService = ratingReportService;
  }

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Long> postReport(@RequestBody RatingReport ratingReport) {
    return ratingReportService.postRatingReport(ratingReport);
  }

  @GetMapping(value = "/{reportId}")
  public ResponseEntity<RatingReport> getRatingReport(@PathVariable Long reportId) {
    return ratingReportService.getRatingReport(reportId);
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<RatingReport>> getAllOpenReports() {
    return ratingReportService.getAllOpenReports();
  }
}
