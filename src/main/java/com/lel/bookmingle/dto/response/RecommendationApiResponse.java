package com.lel.bookmingle.dto.response;

import java.util.List;

public record RecommendationApiResponse(
        List<String> recommendations
) {
}
