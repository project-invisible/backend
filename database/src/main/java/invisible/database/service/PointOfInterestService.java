package invisible.database.service;

import org.springframework.stereotype.Service;

import invisible.database.models.helper.Coordinates;
import invisible.database.models.objects.PoiDTO;
import invisible.database.models.objects.PointOfInterest;
import invisible.database.repository.PointOfInterestRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
public class PointOfInterestService {

  private final String POI_SEQUENCE = "poi_sequence";

  private final PointOfInterestRepository pointOfInterestRepository;
  private final SequenceGeneratorService sequenceGeneratorService;

  public PointOfInterestService(PointOfInterestRepository pointOfInterestRepository, SequenceGeneratorService sequenceGeneratorService) {
    this.pointOfInterestRepository = pointOfInterestRepository;
    this.sequenceGeneratorService = sequenceGeneratorService;
  }

  public List<PointOfInterest> searchForPoi(String query) {
    return pointOfInterestRepository.findByNameLike(query);
  }

  public List<PointOfInterest> getAllPois() {
    return pointOfInterestRepository.findAll();
  }

  public Optional<PointOfInterest> getPoi(Long poiID) {
    return pointOfInterestRepository.findById(poiID);
  }

  public Long postPoi(PointOfInterest poi) {
    poi.setId(sequenceGeneratorService.generateId(POI_SEQUENCE));
    return pointOfInterestRepository.save(poi).getId();
  }

  public String importAll(List<PoiDTO> poiDTOS) {
    List<PointOfInterest> collect = poiDTOS.stream()
        .filter( distinctByKey(PoiDTO::getUniversityLabel))
        .map(poiDTO -> new PointOfInterest(
            sequenceGeneratorService.generateId(POI_SEQUENCE),
            poiDTO.getUniversityLabel(), null, null, null,
            poiDTO.getUniversityDescription(), null,
            poiDTO.getWebsite(), getCoordsFromString(poiDTO.getCoord()), null))
        .collect(Collectors.toList());
    pointOfInterestRepository.saveAll(collect);
    return "";
  }

  private Coordinates getCoordsFromString(String point) {
    return new Coordinates(
        Float.parseFloat(point.substring(point.indexOf("(")+1, point.indexOf(" "))),
        Float.parseFloat(point.substring(point.indexOf(" "), point.indexOf(")")))
    );
  }

  private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
  {
    Map<Object, Boolean> map = new ConcurrentHashMap<>();
    return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }
}
