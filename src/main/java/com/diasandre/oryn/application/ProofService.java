package com.diasandre.oryn.application;

import com.fasterxml.uuid.Generators;
import com.diasandre.oryn.dtos.proof.ProofSummaryDTO;
import com.diasandre.oryn.mappers.ProofMapper;
import com.diasandre.oryn.models.Giveaway;
import com.diasandre.oryn.models.Proof;
import com.diasandre.oryn.models.User;
import com.diasandre.oryn.repositories.GiveawayRepository;
import com.diasandre.oryn.repositories.ProofRepository;
import com.diasandre.oryn.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProofService {
  private final ProofRepository proofRepository;
  private final FileStorageService fileStorageService;
  private final GiveawayRepository giveawayRepository;
  private final UserRepository userRepository;

  @Transactional
  public ProofSummaryDTO
    processAndUpload(MultipartFile file, UUID giveawayId, UUID authorId, String description) {
    String extension = this.getFileExtension(file.getOriginalFilename());
    String fileKey = "proofs/" + Generators.timeBasedEpochGenerator().generate() + extension;

    // upload file
    try {
      this.fileStorageService.uploadFile(file, fileKey);
    } catch (Exception _) {
      throw new RuntimeException("Failed to load file");
    }

    Giveaway giveawayProxy = this.giveawayRepository.getReferenceById(giveawayId);
    User userProxy = this.userRepository.getReferenceById(authorId);
    Proof newProof = new Proof();
    newProof.setAuthor(userProxy);
    newProof.setGiveaway(giveawayProxy);
    newProof.setFileKey(fileKey);
    newProof.setDescription(description);
    newProof.setContentType(file.getContentType());
    newProof.setOriginalName(file.getOriginalFilename());
    newProof = this.proofRepository.save(newProof);
    return ProofMapper.toSummary(newProof);
  }

  public List<ProofSummaryDTO> getGiveawayProofs(UUID giveawayId) {
    return this.proofRepository.findByGiveawayId(giveawayId).stream()
            .map(ProofMapper::toSummary).toList();
  }

  private String getFileExtension(String fileName) {
    if(fileName != null && fileName.contains(".")) {
      return fileName.substring(fileName.lastIndexOf("."));
    }
    return "";
  }
}
