package invivible.database.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import invivible.database.models.objects.PoiDTO;
import invivible.database.models.objects.PointOfInterest;
import invivible.database.service.PointOfInterestService;

import javax.ws.rs.Path;

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
@RestController
@RequestMapping("/poi")
public class PointOfInterestController {

  private final PointOfInterestService pointOfInterestService;

  public PointOfInterestController(PointOfInterestService pointOfInterestService) {
    this.pointOfInterestService = pointOfInterestService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<PointOfInterest>> getAllPois() {
    return new ResponseEntity<>(pointOfInterestService.getAllPois(), HttpStatus.OK);
  }

  @GetMapping("/{poiID}")
  public ResponseEntity<PointOfInterest> getPoi(@PathVariable Long poiID) {
    Optional<PointOfInterest> poi = pointOfInterestService.getPoi(poiID);
    return poi.map(pointOfInterest -> new ResponseEntity<>(pointOfInterest, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping("")
  public ResponseEntity<Long> postPoi(@RequestBody PointOfInterest poi) {
    Long poiID = pointOfInterestService.postPoi(poi);
    return new ResponseEntity<>(poiID, HttpStatus.OK);
  }

  @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<PointOfInterest>> searchForPoi(@RequestParam String query) {
    return new ResponseEntity<>(pointOfInterestService.searchForPoi(query), HttpStatus.OK);
  }

  @PostMapping("/importAll")
  public ResponseEntity<String> importPois(@RequestBody List<PoiDTO> poiDTOS) {
    return new ResponseEntity<>(pointOfInterestService.importAll(poiDTOS), HttpStatus.OK);
  }
}
