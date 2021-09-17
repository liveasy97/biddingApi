package biddingApi.biddingApi.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sharedEntity.BiddingData.Unit;

@NoArgsConstructor
@AllArgsConstructor
public @Data class BidPutRequest {

	private Long currentBid;
	private Long previousBid;
	private Unit unitValue;
	private List<String> truckId;

	private Boolean transporterApproval;
	private Boolean shipperApproval;

	private String biddingDate;
}
