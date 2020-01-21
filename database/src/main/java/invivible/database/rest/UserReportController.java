package invivible.database.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import invivible.database.models.objects.UserReport;
import invivible.database.service.UserReportService;

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
@RequestMapping("/userReport")
public class UserReportController {

  private final String USER_REPORT_SEQUENCE = "user_report_sequence";

  private final UserReportService userReportService;

  public UserReportController(UserReportService userReportService) {
    this.userReportService = userReportService;
  }

  @GetMapping(value = "/{userReportId}")
  public ResponseEntity<UserReport> getUserReport(@PathVariable Long userReportId) {
    return userReportService.getUserReport(userReportId);
  }

  @PostMapping()
  public ResponseEntity<Long> postUserReport(@RequestBody UserReport userReport) {
    return userReportService.postUserReport(userReport);
  }

  @GetMapping()
  public ResponseEntity<List<UserReport>> getAllOpenUserReports() {
    return userReportService.getAllOpenUserReports();
  }
}
