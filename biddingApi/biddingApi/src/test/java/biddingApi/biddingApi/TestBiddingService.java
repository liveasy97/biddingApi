//
//package biddingApi.biddingApi;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import biddingApi.biddingApi.Dao.BiddingDao;
//import biddingApi.biddingApi.Entities.BiddingData;
//import biddingApi.biddingApi.ErrorConstants.Constants;
//import biddingApi.biddingApi.Model.BidDeleteResponse;
//import biddingApi.biddingApi.Model.BidPostRequest;
//import biddingApi.biddingApi.Model.BidPostResponse;
//import biddingApi.biddingApi.Model.BidPutRequest;
//import biddingApi.biddingApi.Model.BidPutResponse;
//import biddingApi.biddingApi.Service.BiddingServiceImpl;
//
//@SpringBootTest
//public class TestBiddingService {
//
//	@Autowired
//	private BiddingServiceImpl biddingService;
//
//	@MockBean
//	private BiddingDao biddingDao;
//
//	@Test
//	public void addDataSuccess() {
//
//		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
//				"load:123", (long) 20, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), null);
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingDao.save(listBiddingData.get(0))).thenReturn(listBiddingData.get(0));
//
//		BidPostResponse bidPostResponse = new BidPostResponse(Constants.success, Constants.ID,
//				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:123", (long) 20, BiddingData.Unit.PER_TON,
//				Arrays.asList("truck:123"), true, false, null);
//
//		assertEquals(bidPostResponse.getStatus(), biddingService.addBid(bidPostRequest).getStatus());
//		assertEquals(bidPostResponse.getTransporterId(), biddingService.addBid(bidPostRequest).getTransporterId());
//		assertEquals(bidPostResponse.getLoadId(), biddingService.addBid(bidPostRequest).getLoadId());
//		assertEquals(bidPostResponse.getCurrentBid(), biddingService.addBid(bidPostRequest).getCurrentBid());
//		assertEquals(bidPostResponse.getUnitValue(), biddingService.addBid(bidPostRequest).getUnitValue());
//		assertEquals(bidPostResponse.getTruckId(), biddingService.addBid(bidPostRequest).getTruckId());
//		assertEquals(bidPostResponse.getTransporterApproval(),
//				biddingService.addBid(bidPostRequest).getTransporterApproval());
//		assertEquals(bidPostResponse.getShipperApproval(), biddingService.addBid(bidPostRequest).getShipperApproval());
//	}
//
//	@Test
//	public void addDataFailed_invalidLoadId_null() {
//
//		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", null,
//				(long) 20, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), null);
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingDao.save(listBiddingData.get(2))).thenReturn(listBiddingData.get(2));
//
//		BidPostResponse response = new BidPostResponse(Constants.pLoadIdIsNull, null, null, null, null, null, null,
//				null, null, null);
//
//		assertEquals(response, biddingService.addBid(bidPostRequest));
//
//	}
//
//	@Test
//	public void addDataFailed_invalidRate_null() {
//		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
//				"load:1345", null, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), null);
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingDao.save(listBiddingData.get(2))).thenReturn(listBiddingData.get(2));
//
//		BidPostResponse response = new BidPostResponse(Constants.pTransporterRateIsNull, null, null, null, null, null,
//				null, null, null, null);
//
//		assertEquals(response, biddingService.addBid(bidPostRequest));
//	}
//
//	@Test
//	public void addDataFailed_invalidUnitValue_null() {
//		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
//				"load:1345", (long) 23, null, Arrays.asList("truck:123"), null);
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingDao.save(listBiddingData.get(2))).thenReturn(listBiddingData.get(2));
//
//		BidPostResponse response = new BidPostResponse(Constants.unitValueisNull, null, null, null, null, null, null,
//				null, null, null);
//
//		assertEquals(response, biddingService.addBid(bidPostRequest));
//	}
//
//	@Test
//	public void addDataFailed_invalidTransporterId_null() {
//		BidPostRequest bidPostRequest = new BidPostRequest(null, "load:123", (long) 20, BiddingData.Unit.PER_TON,
//				Arrays.asList("truck:123"), null);
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingDao.save(listBiddingData.get(2))).thenReturn(listBiddingData.get(5));
//
//		BidPostResponse response = new BidPostResponse(Constants.TRANSPORTER_ID_NULL, null, null, null, null, null,
//				null, null, null, null);
//
//		assertEquals(response, biddingService.addBid(bidPostRequest));
//	}
//
//	@Test
//	public void getBiddingDataWithIdSuccess() {
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));
//
//		assertEquals(listBiddingData.get(0), biddingService.getBidById(Constants.ID));
//
//	}
//
//	@Test
//	public void getBiddingDataWithIdFailed() {
//
//		String wrongBidId = "bid:xyz";
//
//		Optional<BiddingData> EmptyList = Optional.empty();
//
//		when(biddingDao.findById(wrongBidId)).thenReturn(EmptyList);
//
//		assertEquals(null, biddingService.getBidById(wrongBidId));
//
//	}
//
//	@Test
//	public void updateSuccess_Transporter_Approval_True() {
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));
//
//		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, BiddingData.Unit.PER_TRUCK,
//				Arrays.asList("truck:abcdef"), true, null, null);
//
//		BidPutResponse response = new BidPutResponse(Constants.uSuccess, Constants.ID,
//				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:1234", (long) 1000,
//				BiddingData.Unit.PER_TRUCK, Arrays.asList("truck:abcdef"), true, false, null);
//
//		assertEquals(response, biddingService.updateBid(Constants.ID, bidPutRequest));
//
//	}
//
//	@Test
//	public void updateSuccess_Shipper_Approval_True() {
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));
//
//		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, BiddingData.Unit.PER_TRUCK, null, null, true,
//				null);
//
//		BidPutResponse response = new BidPutResponse(Constants.uSuccess, Constants.ID,
//				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:1234", (long) 1000,
//				BiddingData.Unit.PER_TRUCK, Arrays.asList("truck:123"), false, true, null);
//
//		assertEquals(response, biddingService.updateBid(Constants.ID, bidPutRequest));
//
//	}
//
//	@Test
//	public void updateFailed_Shipper_Cannot_Update_TruckID() {
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));
//
//		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, BiddingData.Unit.PER_TRUCK,
//				Arrays.asList("truck:abcdef"), null, true, null);
//
//		BidPutResponse response = new BidPutResponse(Constants.TRUCK_ID_UPDATE_BY_SHIPPER, null, null, null, null, null,
//				null, null, null, null);
//
//		assertEquals(response, biddingService.updateBid(Constants.ID, bidPutRequest));
//
//	}
//
//	@Test
//	public void updateFailed_Transporter_Approval_Shipper_Approval_both_null() {
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));
//
//		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, BiddingData.Unit.PER_TON,
//				Arrays.asList("truck:abcdef"), null, null, null);
//
//		BidPutResponse response = new BidPutResponse(Constants.TRANSPORTER_SHIPPER_APPROVAL_NULL, null, null, null,
//				null, null, null, null, null, null);
//
//		assertEquals(response, biddingService.updateBid(Constants.ID, bidPutRequest));
//
//	}
//
//	@Test
//	public void updateFailed_Transporter_Approval_Shipper_Approval_both_notNull() {
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));
//
//		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, BiddingData.Unit.PER_TON,
//				Arrays.asList("truck:abcdef"), true, true, null);
//
//		BidPutResponse response = new BidPutResponse(Constants.TRANSPORTER_SHIPPER_APPROVAL_NOT_NULL, null, null, null,
//				null, null, null, null, null, null);
//
//		assertEquals(response, biddingService.updateBid(Constants.ID, bidPutRequest));
//
//	}
//
//	@Test
//	public void updateFailed_Unit_null() {
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));
//
//		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, null, null, null, true, null);
//
//		BidPutResponse response = new BidPutResponse(Constants.unitValueisNull, null, null, null, null, null, null,
//				null, null, null);
//
//		assertEquals(response, biddingService.updateBid(Constants.ID, bidPutRequest));
//
//	}
//
//	@Test
//	public void updateDataFailed_AccountNotFound() {
//
//		String wrongBidId = "bid:62cc8557-52cd-4742-a11e-276cc7abcde";
//
//		when(biddingDao.findById(wrongBidId)).thenReturn(Optional.empty());
//
//		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, BiddingData.Unit.PER_TRUCK,
//				Arrays.asList("truck:abc"), true, false, null);
//
//		BidPutResponse response = new BidPutResponse(Constants.uDataNotExists, null, null, null, null, null, null, null,
//				null, null);
//
//		assertEquals(response, biddingService.updateBid(wrongBidId, bidPutRequest));
//
//	}
//
//	@Test
//	public void getTruckDataPagableSuccess_LoadId() {
//
//		Pageable currentPage;
//		Integer pageNo;
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		pageNo = 0;
//		currentPage = PageRequest.of(0, (int) Constants.pageSize);
//
//		when(biddingDao.findByLoadId(Constants.LOAD_ID, currentPage)).thenReturn(listBiddingData.subList(2, 4));
//
//		assertEquals(listBiddingData.subList(2, 4), biddingService.getBid(pageNo, Constants.LOAD_ID, null));
//
//	}
//
//	@Test
//	public void getTruckDataPagableSuccess_TransporterId() {
//
//		Pageable currentPage;
//		Integer pageNo;
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		pageNo = 0;
//		currentPage = PageRequest.of(0, (int) Constants.pageSize);
//
//		when(biddingDao.findByTransporterId(Constants.TRANSPORTER_ID, currentPage))
//				.thenReturn(listBiddingData.subList(0, 2));
//
//		assertEquals(listBiddingData.subList(0, 2), biddingService.getBid(pageNo, null, Constants.TRANSPORTER_ID));
//
//	}
//
//	@Test
//	public void getTruckDataPagableSuccess_LoadId_TransporterId() {
//
//		Pageable currentPage;
//		Integer pageNo;
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		pageNo = 0;
//		currentPage = PageRequest.of(0, (int) Constants.pageSize);
//
//		when(biddingDao.findByLoadIdAndTransporterId(Constants.LOAD_ID, Constants.TRANSPORTER_ID, currentPage))
//				.thenReturn(listBiddingData.subList(4, 5));
//
//		assertEquals(listBiddingData.subList(4, 5),
//				biddingService.getBid(pageNo, Constants.LOAD_ID, Constants.TRANSPORTER_ID));
//
//	}
//
//	@Test
//	public void deleteDataSuccess() {
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));
//
//		BidDeleteResponse response = new BidDeleteResponse(Constants.dSuccess);
//
//		assertEquals(response, biddingService.deleteBid(Constants.ID));
//
//	}
//
//	@Test
//	public void deleteDataFailed_AccountNotFound() {
//
//		String wrongBidId = "bid:xyz";
//
//		Optional<BiddingData> EmptyList = Optional.empty();
//
//		when(biddingDao.findById(wrongBidId)).thenReturn(EmptyList);
//
//		BidDeleteResponse response = new BidDeleteResponse(Constants.dDataNotExists);
//
//		assertEquals(response, biddingService.deleteBid(wrongBidId));
//
//	}
//
//	public List<BiddingData> createBiddingData() {
//		List<BiddingData> biddingList = Arrays.asList(
//				new BiddingData(Constants.ID, Constants.TRANSPORTER_ID, "load:1234", (long) 20,
//						BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), true, false, null),
//				new BiddingData("id1", Constants.TRANSPORTER_ID, null, (long) 20, BiddingData.Unit.PER_TON,
//						Arrays.asList("truck:123"), false, true, null),
//				new BiddingData("id2", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb61", Constants.LOAD_ID, null,
//						BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null),
//				new BiddingData("id3", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb63", Constants.LOAD_ID, null,
//						BiddingData.Unit.PER_TON, Arrays.asList("truck:123", "truck:456"), false, true, null),
//				new BiddingData("id4", Constants.TRANSPORTER_ID, Constants.LOAD_ID, (long) 20, BiddingData.Unit.PER_TON,
//						Arrays.asList("truck:123"), false, true, null),
//				new BiddingData("id5", null, "load:1234", (long) 20, BiddingData.Unit.PER_TON,
//						Arrays.asList("truck:123"), false, true, null)
//
//		);
//
//		return biddingList;
//	}
//
//}