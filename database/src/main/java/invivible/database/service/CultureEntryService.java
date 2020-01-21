package invivible.database.service;

import org.springframework.stereotype.Service;

import invivible.database.models.objects.CultureEntry;
import invivible.database.repository.CultureEntryRepository;

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
public class CultureEntryService {

  private final CultureEntryRepository entryRepository;
  private final SequenceGeneratorService sequenceGeneratorService;

  private final String ENTRY_SEQUENCE = "cultureEntry_sequence";

  public CultureEntryService(CultureEntryRepository entryRepository, SequenceGeneratorService sequenceGeneratorService) {
    this.entryRepository = entryRepository;
    this.sequenceGeneratorService = sequenceGeneratorService;
  }

  public List<CultureEntry> getAllEntries() {
    return entryRepository.findAll();
  }

  public Optional<CultureEntry> getEntry(Long entryID) {
    return entryRepository.findById(entryID);
  }

  public List<CultureEntry> searchForEntry(String query) {
    return entryRepository.findByNameLike(query);
  }

  public Long postCultureEntry(CultureEntry cultureEntry) {
    cultureEntry.setId(sequenceGeneratorService.generateId(ENTRY_SEQUENCE));
    cultureEntry.setCreationDate(new Date());
   return entryRepository.save(cultureEntry).getId();
  }
}
