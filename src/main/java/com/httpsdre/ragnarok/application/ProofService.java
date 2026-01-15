package com.httpsdre.ragnarok.application;

import com.fasterxml.uuid.Generators;
import com.httpsdre.ragnarok.dtos.proof.ProofSummaryDTO;
import com.httpsdre.ragnarok.mappers.ProofMapper;
import com.httpsdre.ragnarok.models.Giveaway;
import com.httpsdre.ragnarok.models.Proof;
import com.httpsdre.ragnarok.models.User;
import com.httpsdre.ragnarok.repositories.GiveawayRepository;
import com.httpsdre.ragnarok.repositories.ProofRepository;
import com.httpsdre.ragnarok.repositories.UserRepository;
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
