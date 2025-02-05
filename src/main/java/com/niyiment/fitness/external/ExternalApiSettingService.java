package com.niyiment.fitness.external;

import com.niyiment.fitness.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ExternalApiSettingService {
    private final ExternalApiSettingRepository repository;

    /**
     * Get setting by API name
     * @param apiName
     * @return
     */
    public ExternalApiSetting getSetting(String apiName) {
       return repository.findByApiName(apiName)
               .orElseThrow(() -> new ResourceNotFoundException("Setting nout found for this API name: " + apiName));
    }

    /**
     * Save or update settings
     * @param setting
     * @return external api setting
     */
    public ExternalApiSetting saveSetting(ExternalApiSetting setting) {
        return repository.save(setting);
    }

}
