package br.com.devschool.demo.domain.service;

import br.com.devschool.demo.domain.model.internal.Version;
import br.com.devschool.demo.domain.model.internal.dto.VersionDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VersionService {
    List<Version> getAllVersions(Integer projectId, Pageable pageable);

    Version getVersionById(Integer id);

    Version createVersion(VersionDTO versionDTO);

    Version updateVersion(Integer id, VersionDTO versionDTO);

    void deleteVersionById(Integer id);
}
