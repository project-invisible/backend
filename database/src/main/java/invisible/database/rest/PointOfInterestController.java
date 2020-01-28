package invisible.database.rest;

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

import invisible.database.models.objects.PoiDTO;
import invisible.database.models.objects.PointOfInterest;
import invisible.database.service.PointOfInterestService;

import java.util.Collections;
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
@RequestMapping("/db/poi")
public class PointOfInterestController {

  private final PointOfInterestService pointOfInterestService;

  public PointOfInterestController(PointOfInterestService pointOfInterestService) {
    this.pointOfInterestService = pointOfInterestService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<PointOfInterest>> getAllPois() {
    return new ResponseEntity<>(pointOfInterestService.getAllPois(), HttpStatus.OK);
  }

  @GetMapping(value = "/{poiID}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PointOfInterest> getPoi(@PathVariable Long poiID) {
    Optional<PointOfInterest> poi = pointOfInterestService.getPoi(poiID);
    return poi.map(pointOfInterest -> new ResponseEntity<>(pointOfInterest, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Long> postPoi(@RequestBody PointOfInterest poi) {
    Long poiID = pointOfInterestService.postPoi(poi);
    return new ResponseEntity<>(poiID, HttpStatus.OK);
  }

  @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<PointOfInterest>> searchForPoi(@RequestParam String query) {
    if(query == null || query.equals("")) {
      return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }
    return new ResponseEntity<>(pointOfInterestService.searchForPoi(query), HttpStatus.OK);
  }

  @PostMapping(value = "/importAll", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> importPois(@RequestBody List<PoiDTO> poiDTOS) {
    return new ResponseEntity<>(pointOfInterestService.importAll(poiDTOS), HttpStatus.OK);
  }
}
