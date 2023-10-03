package com.lel.bookmingle.dto.request;

import com.lel.bookmingle.enums.RequestStatus;
import com.lel.bookmingle.enums.RequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRequest implements Serializable {

    private Integer requesterUserId;
    private Integer proposedBookId;
    private Integer requestedUserId;
    private Integer requestedBookId;
    private RequestType requestType;
    private RequestStatus requestStatus;
}
