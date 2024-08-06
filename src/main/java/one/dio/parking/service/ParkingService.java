package one.dio.parking.service;

import jakarta.transaction.Transactional;
import one.dio.parking.exception.ParkingNotFoundExceptions;
import one.dio.parking.model.Parking;
import one.dio.parking.repository.ParkingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ParkingService {
    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Transactional()
    public List<Parking> findAll() {
        return parkingRepository.findAll();
    }

    @Transactional()
    public Parking findById(String id) {
        return parkingRepository.findById(id).orElseThrow(
                () -> new ParkingNotFoundExceptions(id));
    }

    @Transactional
    public Parking create(Parking parkingCreate) {
        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setEntryDate(LocalDateTime.now());
        parkingRepository.save(parkingCreate);
        return parkingCreate;
    }

    @Transactional
    public void delete(String id) {
        findById(id);
        parkingRepository.deleteById(id);
    }

    @Transactional
    public Parking update(String id, Parking parkingCreate) {
        Parking parking = findById(id);
        parking.setColor(parkingCreate.getColor());
        parking.setState(parkingCreate.getState());
        parking.setModel(parkingCreate.getModel());
        parking.setLicense(parkingCreate.getLicense());
        parkingRepository.save(parking);
        return parking;
    }

    @Transactional
    public Parking checkOut(String id) {
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(ParkingCheckout.getBill(parking));
        return parkingRepository.save(parking);
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
