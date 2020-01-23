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

import invivible.database.models.objects.CultureEntry;
import invivible.database.service.CultureEntryService;

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
@RequestMapping("/entry")
public class CultureEntryController {

  private final CultureEntryService entryService;

  public CultureEntryController(CultureEntryService entryService) {
    this.entryService = entryService;
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CultureEntry>> getAllEntries() {
    return new ResponseEntity<>(entryService.getAllEntries(), HttpStatus.OK);
  }

  @GetMapping(value = "/{entryID}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CultureEntry> getEntry(@PathVariable Long entryID) {
    Optional<CultureEntry> entry = entryService.getEntry(entryID);
    return entry.map(entry1 -> new ResponseEntity<>(entry1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CultureEntry>> searchForEntry(@RequestParam String query) {
    return new ResponseEntity<>(entryService.searchForEntry(query), HttpStatus.OK);
  }

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> postCultureEntry(@RequestBody CultureEntry cultureEntry) {
    Long entryId = entryService.postCultureEntry(cultureEntry);
    return new ResponseEntity<>("Entry saved with Id: " + entryId, HttpStatus.OK);
  }

}
