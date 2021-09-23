
package biddingApi.biddingApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import biddingApi.biddingApi.ErrorConstants.Constants;
import biddingApi.biddingApi.Exception.BusinessException;
import biddingApi.biddingApi.Exception.EntityNotFoundException;
import biddingApi.biddingApi.Model.BidDeleteResponse;
import biddingApi.biddingApi.Model.BidPostRequest;
import biddingApi.biddingApi.Model.BidPostResponse;
import biddingApi.biddingApi.Model.BidPutRequest;
import biddingApi.biddingApi.Model.BidPutResponse;
import biddingApi.biddingApi.Service.BiddingServiceImpl;
import sharedDao.BiddingDao;
import sharedEntity.BiddingData;

@SpringBootTest
public class TestBiddingService {

	@Autowired
	private BiddingServiceImpl biddingService;

	@MockBean
	private BiddingDao biddingDao;

	@Test
	public void addDataSuccess() {

		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"load:123", (long) 20, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), null);

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.save(listBiddingData.get(0))).thenReturn(listBiddingData.get(0));

		BidPostResponse bidPostResponse = new BidPostResponse(Constants.success, Constants.ID,
				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:123", (long) 20, null, BiddingData.Unit.PER_TON,
				Arrays.asList("truck:123"), true, false, null);

		assertEquals(bidPostResponse.getStatus(), biddingService.addBid(bidPostRequest, null).getStatus());
		assertEquals(bidPostResponse.getTransporterId(), biddingService.addBid(bidPostRequest, null).getTransporterId());
		assertEquals(bidPostResponse.getLoadId(), biddingService.addBid(bidPostRequest, null).getLoadId());
		assertEquals(bidPostResponse.getCurrentBid(), biddingService.addBid(bidPostRequest, null).getCurrentBid());
		assertEquals(bidPostResponse.getUnitValue(), biddingService.addBid(bidPostRequest, null).getUnitValue());
		assertEquals(bidPostResponse.getTruckId(), biddingService.addBid(bidPostRequest, null).getTruckId());
		assertEquals(bidPostResponse.getTransporterApproval(),
				biddingService.addBid(bidPostRequest, null).getTransporterApproval());
		assertEquals(bidPostResponse.getShipperApproval(), biddingService.addBid(bidPostRequest, null).getShipperApproval());
	}


	/**
	 * Null check on loadId is not happening in service layer,instead its happening in Dao layer.
	 * Hence this test case is failing currently.
	 */
	@Test
	public void addDataFailed_invalidLoadId_null() {

		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", null,
				(long) 20, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), null);

//		List<BiddingData> listBiddingData = createBiddingData();
//		when(biddingDao.save(listBiddingData.get(2))).thenReturn(listBiddingData.get(2));

		BidPostResponse expected = new BidPostResponse(Constants.pLoadIdIsNull, Constants.ID,
				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:123", (long) 20, null, BiddingData.Unit.PER_TON,
				Arrays.asList("truck:123"), true, false, null);
		
		BidPostResponse actual = biddingService.addBid(bidPostRequest, null);
		
		// skip BidId check as it is randomly generated in addBid() method //
		expected.setBidId(actual.getBidId());
		
		assertEquals(expected, actual);

	}

	@Test
	public void addDataFailed_invalidRate_null() {
		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"load:1345", null, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), null);

//		List<BiddingData> listBiddingData = createBiddingData();
//		when(biddingDao.save(listBiddingData.get(2))).thenReturn(listBiddingData.get(2));
		
		BidPostResponse expected = new BidPostResponse(Constants.CURRENT_BID_NULL, Constants.ID,
				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:123", (long) 20, null, BiddingData.Unit.PER_TON,
				Arrays.asList("truck:123"), true, false, null);
		
		BidPostResponse actual = biddingService.addBid(bidPostRequest, null);
		
		// skip BidId check as it is randomly generated in addBid() method //
		expected.setBidId(actual.getBidId());
		
		assertEquals(expected,actual);
	}

	@Test
	public void addDataFailed_invalidUnitValue_null() {
		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"load:1345", (long) 23, null, Arrays.asList("truck:123"), null);

		BusinessException ex = assertThrows(BusinessException.class, () -> {
			biddingService.addBid(bidPostRequest, null);
		});

		assertTrue(ex.getMessage().contains(Constants.UnknownUnit));
	}

	@Test
	public void addDataFailed_invalidTransporterId_null() {
		BidPostRequest bidPostRequest = new BidPostRequest(null, "load:123", (long) 20, BiddingData.Unit.PER_TON,
				Arrays.asList("truck:123"), null);

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.save(listBiddingData.get(2))).thenReturn(listBiddingData.get(5));

		BidPostResponse expected = new BidPostResponse(Constants.TRANSPORTER_ID_NULL, Constants.ID,
				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:123", (long) 20, null, BiddingData.Unit.PER_TON,
				Arrays.asList("truck:123"), true, false, null);

		BidPostResponse actual = biddingService.addBid(bidPostRequest, null);
		
		// skip BidId check as it is randomly generated in addBid() method //
		expected.setBidId(actual.getBidId());
		
		assertEquals(expected,actual);
	}

	@Test
	public void getBiddingDataWithIdSuccess() {

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));

		assertEquals(listBiddingData.get(0), biddingService.getBidById(Constants.ID, null));

	}

	@Test
	public void getBiddingDataWithIdFailed() {

		String wrongBidId = "bid:xyz";

		Optional<BiddingData> EmptyList = Optional.empty();

		when(biddingDao.findById(wrongBidId)).thenReturn(EmptyList);

		assertThrows(EntityNotFoundException.class, () -> {
			biddingService.getBidById(wrongBidId, null);
		});	

	}

	@Test
	public void updateSuccess_Transporter_Approval_True() {

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, null, BiddingData.Unit.PER_TRUCK,
				Arrays.asList("truck:abcdef"), true, null, null);

		BidPutResponse response = new BidPutResponse(Constants.uSuccess, Constants.ID,
				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:1234", (long) 1000,
				(long)20, BiddingData.Unit.PER_TRUCK, Arrays.asList("truck:abcdef"), true, false, null);

		assertEquals(response, biddingService.updateBid(Constants.ID, bidPutRequest, null));

	}

	@Test
	public void updateSuccess_Shipper_Approval_True() {

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, null, BiddingData.Unit.PER_TRUCK, null, null, true,
				null);

		BidPutResponse response = new BidPutResponse(Constants.uSuccess, Constants.ID,
				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:1234", (long) 1000,
				(long)20, BiddingData.Unit.PER_TRUCK, Arrays.asList("truck:123"), false, true, null);

		assertEquals(response, biddingService.updateBid(Constants.ID, bidPutRequest, null));

	}

	@Test
	public void updateFailed_Shipper_Cannot_Update_TruckID() {

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, null, BiddingData.Unit.PER_TRUCK,
				Arrays.asList("truck:abcdef"), null, true, null);

		BusinessException ex = assertThrows(BusinessException.class, () -> {
			biddingService.updateBid(Constants.ID, bidPutRequest, null);
		});

		assertTrue(ex.getMessage().contains(Constants.TRUCK_ID_UPDATE_BY_SHIPPER ));

	}

	@Test
	public void updateFailed_Transporter_Approval_Shipper_Approval_both_null() {

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, null, BiddingData.Unit.PER_TON,
				Arrays.asList("truck:abcdef"), null, null, null);
		
		BusinessException ex = assertThrows(BusinessException.class, () -> {
			biddingService.updateBid(Constants.ID, bidPutRequest, null);
		});

		assertTrue(ex.getMessage().contains(Constants.TRANSPORTER_SHIPPER_APPROVAL_NULL ));

	}

	@Test
	public void updateFailed_Transporter_Approval_Shipper_Approval_both_notNull() {

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, null, BiddingData.Unit.PER_TON,
				Arrays.asList("truck:abcdef"), true, true, null);

		BusinessException ex = assertThrows(BusinessException.class, () -> {
			biddingService.updateBid(Constants.ID, bidPutRequest, null);
		});	
		
		assertTrue(ex.getMessage().contains(Constants.TRANSPORTER_SHIPPER_APPROVAL_NOT_NULL ));

	}

	@Test
	public void updateFailed_Unit_null() {

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, null, null, null, true, null, null);

		BusinessException ex = assertThrows(BusinessException.class, () -> {
			biddingService.updateBid(Constants.ID, bidPutRequest, null);
		});	
		
		assertFalse(ex.getMessage().contains(Constants.TRANSPORTER_SHIPPER_APPROVAL_NOT_NULL));

	}

	@Test
	public void updateDataFailed_AccountNotFound() {

		String wrongBidId = "bid:62cc8557-52cd-4742-a11e-276cc7abcde";

		when(biddingDao.findById(wrongBidId)).thenReturn(Optional.empty());

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, null, BiddingData.Unit.PER_TRUCK,
				Arrays.asList("truck:abc"), true, false, null);

		assertThrows(EntityNotFoundException.class, () -> {
			biddingService.updateBid(wrongBidId, bidPutRequest, null);
		});	

	}

	@Test
	public void getTruckDataPagableSuccess_LoadId() {

		Pageable currentPage;
		Integer pageNo;

		List<BiddingData> listBiddingData = createBiddingData();

		pageNo = 0;
		currentPage = PageRequest.of(pageNo, Constants.pageSize, Sort.Direction.DESC, "timestamp");

		when(biddingDao.findByLoadId(Constants.LOAD_ID, currentPage)).thenReturn(listBiddingData.subList(2, 4));

		assertEquals(listBiddingData.subList(2, 4), biddingService.getBid(pageNo, Constants.LOAD_ID, null, null));

	}

	@Test
	public void getTruckDataPagableSuccess_TransporterId() {

		Pageable currentPage;
		Integer pageNo;

		List<BiddingData> listBiddingData = createBiddingData();

		pageNo = 0;
		currentPage = PageRequest.of(pageNo, Constants.pageSize, Sort.Direction.DESC, "timestamp");

		when(biddingDao.findByTransporterId(Constants.TRANSPORTER_ID, currentPage))
				.thenReturn(listBiddingData.subList(0, 2));

		assertEquals(listBiddingData.subList(0, 2), biddingService.getBid(pageNo, null, Constants.TRANSPORTER_ID, null));

	}

	@Test
	public void getTruckDataPagableSuccess_LoadId_TransporterId() {

		Pageable currentPage;
		Integer pageNo;

		List<BiddingData> listBiddingData = createBiddingData();

		pageNo = 0;
		currentPage = PageRequest.of(pageNo, Constants.pageSize, Sort.Direction.DESC, "timestamp");

		when(biddingDao.findByLoadIdAndTransporterId(Constants.LOAD_ID, Constants.TRANSPORTER_ID, currentPage))
				.thenReturn(listBiddingData.subList(4, 5));

		assertEquals(listBiddingData.subList(4, 5),
				biddingService.getBid(pageNo, Constants.LOAD_ID, Constants.TRANSPORTER_ID, null));

	}

	@Test
	public void deleteDataSuccess() {

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));

		BidDeleteResponse response = new BidDeleteResponse(Constants.dSuccess);

		assertEquals(response, biddingService.deleteBid(Constants.ID, null));

	}
	
	@Test
	public void deleteDataFailed_AccountNotFound() {
		
		String wrongBidId = "bid:xyz";
		
		Optional<BiddingData> EmptyList = Optional.empty();
		
		when(biddingDao.findById(wrongBidId)).thenReturn(EmptyList);
		
		assertThrows(EntityNotFoundException.class, () -> {
			biddingService.getBidById(wrongBidId, null);
		});
		
	}
	
	BiddingServiceImpl biddingServiceImpl = new BiddingServiceImpl();
	
	public List<BiddingData> createBiddingData() {
		List<BiddingData> biddingList = Arrays.asList(
				new BiddingData(Constants.ID, Constants.TRANSPORTER_ID, "load:1234", (long) 20,
						null, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), true, false, null, null),
				new BiddingData("id1", Constants.TRANSPORTER_ID, null, (long) 20, null, BiddingData.Unit.PER_TON,
						Arrays.asList("truck:123"), false, true, null, null),
				new BiddingData("id2", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb61", Constants.LOAD_ID, null,
						null, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null, null),
				new BiddingData("id3", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb63", Constants.LOAD_ID, null,
						null, BiddingData.Unit.PER_TON, Arrays.asList("truck:123", "truck:456"), false, true, null, null),
				new BiddingData("id4", Constants.TRANSPORTER_ID, Constants.LOAD_ID, (long) 20, null, BiddingData.Unit.PER_TON,
						Arrays.asList("truck:123"), false, true, null, null),
				new BiddingData("id5", null, "load:1234", (long) 20, null, BiddingData.Unit.PER_TON,
						Arrays.asList("truck:123"), false, true, null, null)

		);
		return biddingList;
	}


}