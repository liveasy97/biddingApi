//package biddingApi.biddingApi;
//
//import static org.assertj.core.api.Assertions.assertThat;
////import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import biddingApi.biddingApi.Controller.BiddingController;
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
//@WebMvcTest(value = BiddingController.class)
//class TestBiddingController {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private BiddingDao biddingDao;
//
//	@MockBean
//	private BiddingServiceImpl biddingService;
//
//	private static String mapToJson(Object object) throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
//		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
//		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
//
//		return objectMapper.writeValueAsString(object);
//	}
//
//	@Test
//	public void addData() throws Exception {
//
//		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
//				"load:123", (long) 20, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), null);
//
//		BidPostResponse bidPostResponse = new BidPostResponse(Constants.success, Constants.ID,
//				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:123", (long) 20, BiddingData.Unit.PER_TON,
//				Arrays.asList("truck:123"), true, false, null);
//
//		when(biddingService.addBid(Mockito.any(BidPostRequest.class))).thenReturn(bidPostResponse);
//
//		String inputJson = mapToJson(bidPostRequest);
//
//		String expectedJson = mapToJson(bidPostResponse);
//
//		String URI = Constants.URI;
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
//				.content(inputJson).contentType(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		MockHttpServletResponse response = result.getResponse();
//
//		String outputInJson = response.getContentAsString();
//
//		assertThat(outputInJson).isEqualTo(expectedJson);
//		assertEquals(HttpStatus.OK.value(), response.getStatus());
//
//	}
//
//	@Test
//	public void getBiddingDataWithId() throws Exception {
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingService.getBidById(Constants.ID)).thenReturn(listBiddingData.get(0));
//
//		String URI = Constants.BID_ID_URI;
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		MockHttpServletResponse response = result.getResponse();
//
//		String expectedJson = mapToJson(listBiddingData.get(0));
//		String outputInJson = result.getResponse().getContentAsString();
//
//		assertEquals(HttpStatus.OK.value(), response.getStatus());
//		assertEquals(expectedJson, outputInJson);
//
//	}
//
//	@Test
//	public void getBiddingDataWithParameters() throws Exception {
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingService.getBid(0, Constants.LOAD_ID, Constants.TRANSPORTER_ID))
//				.thenReturn(listBiddingData.subList(4, 5));
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(Constants.URI)
//				.queryParam("loadId", Constants.LOAD_ID).queryParam("pageNo", String.valueOf(0))
//				.queryParam("transporterId", Constants.TRANSPORTER_ID).accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		MockHttpServletResponse response = result.getResponse();
//
//		String outputInJson = response.getContentAsString();
//		String expectedJson = mapToJson(listBiddingData.subList(4, 5));
//
//		assertEquals(expectedJson, outputInJson);
//		assertEquals(HttpStatus.OK.value(), response.getStatus());
//
//	}
//
//	@Test
//	public void updateData() throws Exception {
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingService.getBidById(Constants.ID)).thenReturn(listBiddingData.get(0));
//
//		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, BiddingData.Unit.PER_TRUCK,
//				Arrays.asList("truck:abc"), true, false, null);
//
//		BidPutResponse response = new BidPutResponse(Constants.uSuccess, Constants.ID,
//				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:123", (long) 20, BiddingData.Unit.PER_TRUCK,
//				Arrays.asList("truck:123"), true, false, null);
//
//		String inputJson = mapToJson(bidPutRequest);
//
//		String expectedJson = mapToJson(response);
//
//		when(biddingService.updateBid(Constants.ID, bidPutRequest)).thenReturn(response);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(Constants.BID_ID_URI)
//				.accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		MockHttpServletResponse response1 = result.getResponse();
//		String outputInJson = result.getResponse().getContentAsString();
//
//		assertEquals(expectedJson, outputInJson);
//		assertEquals(HttpStatus.OK.value(), response1.getStatus());
//
//	}
//
//	@Test
//	public void deleteData() throws Exception {
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingService.getBidById(Constants.ID)).thenReturn(listBiddingData.get(0));
//
//		BidDeleteResponse response = new BidDeleteResponse(Constants.dSuccess);
//
//		String expectedJson = mapToJson(response);
//
//		when(biddingService.deleteBid(Constants.ID)).thenReturn(response);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(Constants.BID_ID_URI)
//				.accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		MockHttpServletResponse response1 = result.getResponse();
//		String outputInJson = result.getResponse().getContentAsString();
//
//		assertEquals(expectedJson, outputInJson);
//		assertEquals(HttpStatus.OK.value(), response1.getStatus());
//
//	}
//
//	public List<BiddingData> createBiddingData() {
//		List<BiddingData> biddingList = Arrays.asList(
//				new BiddingData(Constants.ID, Constants.TRANSPORTER_ID, "load:1234", (long) 20, null,
//						BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null),
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
//}
