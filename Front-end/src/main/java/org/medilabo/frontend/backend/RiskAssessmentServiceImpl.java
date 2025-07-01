package org.medilabo.frontend.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medilabo.frontend.dto.RiskAssessmentResponse;
import org.medilabo.frontend.exceptions.RiskAssessmentException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RiskAssessmentServiceImpl implements RiskAssessmentService{
    private final RiskAssessmentClient riskClient;

    @Override
    public RiskAssessmentResponse assessPatient(Long patientId) {
        try {
            return riskClient.assessPatient(patientId);
        } catch (FeignException.BadRequest e) {

            String errorMessage = extractErrorMessage(e);
            log.warn("Bad request while assessing patient {}: {}", patientId, errorMessage);
            throw new RiskAssessmentException(errorMessage);
        } catch (FeignException.NotFound e) {
            String errorMessage = extractErrorMessage(e);
            log.warn("patient with id {} was not found: {}", patientId, errorMessage);
            throw new RiskAssessmentException(errorMessage);
        }catch (Exception e) {
            log.error("Error assessing patient {}: {}", patientId, e.getMessage());
            throw new RiskAssessmentException("Unable to assess patient risk");
        }


    }

    private String extractErrorMessage(FeignException exception) {
        try {
            String responseBody = exception.contentUTF8();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);
            return root.path("message").asText("Unable to assess patient risk");
        } catch (Exception ex) {
            return "Unable to assess patient risk";
        }
    }
}
