package invivible.database.service;

import org.springframework.stereotype.Service;

import invivible.database.models.objects.Entry;
import invivible.database.repository.EntryRepository;

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
public class EntryService {

  private final EntryRepository entryRepository;

  public EntryService(EntryRepository entryRepository) {
    this.entryRepository = entryRepository;
  }

  public List<Entry> getAllEntries() {
    return entryRepository.findAll();
  }

  public Optional<Entry> getEntry(Long entryID) {
    return entryRepository.findById(entryID);
  }

  public List<Entry> searchForEntry(String query) {
    return entryRepository.findByNameLike(query);
  }
}
