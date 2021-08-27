package biddingApi.biddingApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import biddingApi.biddingApi.Dao.BiddingDao;
import biddingApi.biddingApi.Entities.BiddingData;
import biddingApi.biddingApi.Exception.EntityNotFoundException;
import biddingApi.biddingApi.Service.BiddingServiceImpl;

@SpringBootTest
public class BiddingServiceTest {
	
	@InjectMocks
	private BiddingServiceImpl bsi;
	
	@Mock
	private BiddingDao dao;
	
	@Test
	public void TestGetBidById_WhenNotFound()
	{
		String id ="testid";
		when(dao.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(EntityNotFoundException.class, () -> {
			bsi.getBidById(id, null);
		});		
	}
	
	@Test
	public void TestGetBidById_WhenFound()
	{
		String id ="testid";
		BiddingData bdataRequired = new BiddingData();
		bdataRequired.setBidId("bid1");
		bdataRequired.setCurrentBid(400L);
	
		when(dao.findById(id)).thenReturn(Optional.of(bdataRequired));
		
		BiddingData bdatafromMethod = bsi.getBidById(id, null);
		assertEquals(bdatafromMethod.getBidId(),bdataRequired.getBidId());
		assertEquals(bdatafromMethod.getCurrentBid(),bdataRequired.getCurrentBid());
	
	}
}
