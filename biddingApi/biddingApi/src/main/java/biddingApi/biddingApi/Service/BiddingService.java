package biddingApi.biddingApi.Service;

import java.util.List;

import biddingApi.biddingApi.Entities.BiddingData;
import biddingApi.biddingApi.Model.BidDeleteResponse;
import biddingApi.biddingApi.Model.BidPostRequest;
import biddingApi.biddingApi.Model.BidPostResponse;
import biddingApi.biddingApi.Model.BidPutRequest;
import biddingApi.biddingApi.Model.BidPutResponse;

public interface BiddingService {

	public BidPostResponse addBid(BidPostRequest bidPostRequest,String token);

	public List<BiddingData> getBid(Integer pageNo, String loadId, String transporterId,String token);

	public BidDeleteResponse deleteBid(String id,String token);

	public BiddingData getBidById(String id,String token);

	public BidPutResponse updateBid(String id, BidPutRequest bidPutRequest,String token);

}
