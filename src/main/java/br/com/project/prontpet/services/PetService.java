package br.com.project.prontpet.services;

import br.com.project.prontpet.models.Owner;
import br.com.project.prontpet.models.Pet;
import br.com.project.prontpet.repositories.AppointmentRepository;
import br.com.project.prontpet.repositories.OwnerRepository;
import br.com.project.prontpet.repositories.PetRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PetService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public PetService(AppointmentRepository appointmentRepository, PetRepository petRepository, OwnerRepository ownerRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    @Cacheable(value = "pets")
    public Page<Pet> getPets(Pageable pageable){
        return petRepository.findAll(pageable);
    }

    public Optional<Pet> getPetById(Long id){
        return petRepository.findById(id);
    }

    //Filtro por Raça
    @Cacheable(value = "breeds")
    public Page<Pet> getByBreed(String breed, Pageable pageable){
        return petRepository.findByBreedContainingIgnoreCase(breed, pageable);
    }

    //Filtro por idade
    @Cacheable(value = "dates")
    public Page<Pet> getByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable){
        return  petRepository.findByBirthDateBetween(startDate, endDate, pageable);
    }

    //Filtro por Species
    @Cacheable(value = "species")
    public Page<Pet> getBySpecies(String species, Pageable pageable){
        return petRepository.findBySpeciesContainingIgnoreCase(species, pageable);
    }

    @Cacheable(value = "names")
    public Page<Pet> getByName(String name, Pageable pageable){
        return petRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @CacheEvict(value = {"pets", "breeds", "dates", "species", "names"}, allEntries = true)
    public Pet addPet(Pet pet, Long ownerId){
        if (ownerId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ownerId is required");
        }

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found"));

        pet.setOwner(owner);

        return petRepository.save(pet);
    }

    @CacheEvict(value = {"pets", "breeds", "dates", "species", "names"}, allEntries = true)
    public void deletePet (Long id) {
        var optionalPet = getPetById(id);
        if (optionalPet.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
        Pet existingPet = optionalPet.get();

        boolean isAppointmentScheduled = appointmentRepository.hasFutureOrTodayAppointments(existingPet.getId(), LocalDateTime.now());

        if (isAppointmentScheduled) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "has a scheduled appointment");
        }

        petRepository.deleteById(id);}

    @CacheEvict(value = {"pets", "breeds", "dates", "species", "names"}, allEntries = true)
    public Pet updatePet(Long id, Pet newPet){

        var optionalPet = getPetById(id);
        if (optionalPet.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
        Pet existingPet = optionalPet.get();

        newPet.setOwner(existingPet.getOwner());
        newPet.setId(id);
        petRepository.save(newPet);
        return newPet;
    }
}
