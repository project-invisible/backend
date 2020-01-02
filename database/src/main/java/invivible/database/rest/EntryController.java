package invivible.database.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import invivible.database.models.objects.Entry;
import invivible.database.service.EntryService;

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
public class EntryController {

  private final EntryService entryService;

  public EntryController(EntryService entryService) {
    this.entryService = entryService;
  }

  @GetMapping()
  public ResponseEntity<List<Entry>> getAllEntries() {
    return new ResponseEntity<>(entryService.getAllEntries(), HttpStatus.OK);
  }

  @GetMapping("/{entryID}")
  public ResponseEntity<Entry> getEntry(@PathVariable Long entryID) {
    Optional<Entry> entry = entryService.getEntry(entryID);
    return entry.map(entry1 -> new ResponseEntity<>(entry1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping("/search")
  public ResponseEntity<List<Entry>> searchForEntry(@RequestBody String query) {
    return new ResponseEntity<>(entryService.searchForEntry(query), HttpStatus.OK);
  }

}
