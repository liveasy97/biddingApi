package biddingApi.biddingApi.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sharedEntity.BiddingData.Unit;

@NoArgsConstructor
@AllArgsConstructor
public @Data class BidPostRequest {

	private String transporterId;
	private String loadId;
	private Long currentBid;

	private Unit unitValue;
	private List<String> truckId;
	private String biddingDate;
}
