package biddingApi.biddingApi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import biddingApi.biddingApi.Dao.BiddingDao;
import biddingApi.biddingApi.Entities.BiddingData;
import biddingApi.biddingApi.ErrorConstants.Constants;


@DataJpaTest
public class TestBiddingDao {
	
	public static void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private BiddingDao biddingDao;

	@Test
	public void testFindById() {

		List<BiddingData> listBiddingData = createBiddingData();

		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
		Optional<BiddingData> getFromDb = biddingDao.findById(Constants.ID);

		assertThat(getFromDb).isEqualTo(Optional.ofNullable((savedInDb)));

	}

	@Test
	public void testFindByLoadId() {

		List<BiddingData> listBiddingData = createBiddingData();

		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(1));
		BiddingData savedInDb2 = entityManager.persist(listBiddingData.get(2));
		BiddingData savedInDb3 = entityManager.persist(listBiddingData.get(3));
		BiddingData savedInDb4 = entityManager.persist(listBiddingData.get(4));
		BiddingData savedInDb5 = entityManager.persist(listBiddingData.get(5));

		List<BiddingData> allBids = biddingDao.findByLoadId(Constants.LOAD_ID);
		
		assertThat(allBids.size()).isEqualTo(4);
		
		for(BiddingData data : allBids) {
			assertTrue(listBiddingData.contains(data));
		}
	}

	@Test
	public void testFindByLoadIdWithPagination() {

		Pageable currentPage;
		List<BiddingData> listBiddingData = createBiddingData();

		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(1));
		BiddingData savedInDb2 = entityManager.persist(listBiddingData.get(2));
		BiddingData savedInDb3 = entityManager.persist(listBiddingData.get(3));
		BiddingData savedInDb4 = entityManager.persist(listBiddingData.get(4));
		BiddingData savedInDb5 = entityManager.persist(listBiddingData.get(5));

		currentPage = PageRequest.of(0, (int) Constants.pageSize);

		List<BiddingData> allBids = biddingDao.findByLoadId("load:123", currentPage);

		assertThat(allBids.size()).isEqualTo(4);
		for(BiddingData data : allBids) {
			assertTrue(listBiddingData.contains(data));
		}

		Pageable nextPage = PageRequest.of(1, (int) Constants.pageSize);
		List<BiddingData> allBidsNextPage = biddingDao.findByLoadId("load:123", nextPage);

		assertThat(allBidsNextPage.size()).isEqualTo(0);
		for(BiddingData data : allBidsNextPage) {
			assertTrue(listBiddingData.contains(data));
		}
	}

	@Test
	public void testFindByTransporterIdWithPagination() {

		List<BiddingData> transporterId1 = new ArrayList<BiddingData>();
		List<BiddingData> transporterId2 = new ArrayList<BiddingData>();
		for(int i=11; i<=28; i++)
		{
			boolean firstTransporter;
			if (i%2 == 1) {
				firstTransporter = true;
			}else {
				firstTransporter = false;
			}
			
			String transporterId = firstTransporter ? "tr:1" : "tr:2";
			
			BiddingData savedInDb = entityManager.persist(new BiddingData("bid:"+i, transporterId, "load:"+i, (long) 20,
					null, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null, Timestamp.valueOf("2021-07-28 23:28:50.134")));
			
			entityManager.flush();
			
			if(firstTransporter) transporterId1.add(savedInDb);
			else  transporterId2.add(savedInDb);
			
			wait(1);
		}
		
		Collections.reverse(transporterId1);
		Collections.reverse(transporterId2);
		
		PageRequest firstPage = PageRequest.of(0, 5, Sort.Direction.DESC, "timestamp"),
		            secondPage = PageRequest.of(1, 5, Sort.Direction.DESC, "timestamp"),
		            thirdPage = PageRequest.of(2, 5, Sort.Direction.DESC, "timestamp");
		assertThat(transporterId1.subList(0, 5)).isEqualTo(biddingDao.findByTransporterId("tr:1",firstPage));
	    assertThat(transporterId1.subList(5, 9)).isEqualTo(biddingDao.findByTransporterId("tr:1",secondPage));
	    assertThat(transporterId1.subList(9, 9)).isEqualTo(biddingDao.findByTransporterId("tr:1",thirdPage));
	    
	    assertThat(transporterId2.subList(0, 5)).isEqualTo(biddingDao.findByTransporterId("tr:2",firstPage));
	    assertThat(transporterId2.subList(5, 9)).isEqualTo(biddingDao.findByTransporterId("tr:2",secondPage));
	    assertThat(transporterId2.subList(9, 9)).isEqualTo(biddingDao.findByTransporterId("tr:2",thirdPage));
	
	}
	

	//there will be only one record for tranporterid and loadid combination
	@Test
	public void testFindByLoadIdAndTransporterIdWithPagination() {

		Pageable currentPage;
		List<BiddingData> listBiddingData = createBiddingData();

		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(1));
		BiddingData savedInDb2 = entityManager.persist(listBiddingData.get(2));

		currentPage = PageRequest.of(0, (int) Constants.pageSize);
		List<BiddingData> allBids = biddingDao.findByLoadIdAndTransporterId(Constants.LOAD_ID,
				Constants.TRANSPORTER_ID, currentPage);

		assertThat(allBids.size()).isEqualTo(1);
		assertEquals(savedInDb1,allBids.get(0));
	}
	
	@Test
	public void testFindByLoadIdAndTransporterIdWithOutPagination() {

		List<BiddingData> listBiddingData = createBiddingData();

		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(1));
		BiddingData savedInDb2 = entityManager.persist(listBiddingData.get(2));

		BiddingData fromDao = biddingDao.findByLoadIdAndTransporterId(Constants.LOAD_ID,
				Constants.TRANSPORTER_ID);
		assertEquals(savedInDb1,fromDao);
		
	}

	@Test
	public void testUpdate() {

		List<BiddingData> listBiddingData = createBiddingData();
		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
		
		listBiddingData.get(0).setCurrentBid((long) 101);
		
		entityManager.persist(listBiddingData.get(0));
		
		Optional<BiddingData> getFromDb = biddingDao.findById(Constants.ID);
		
		assertEquals(101,getFromDb.get().getCurrentBid());
	
	}
	
	@Test
	public void testDelete() {

		List<BiddingData> listBiddingData = createBiddingData();

		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(1));

		entityManager.remove(savedInDb1);

		List<BiddingData> allBids = biddingDao.findAll();

		assertThat(allBids.size()).isEqualTo(1);
		for (BiddingData data : allBids) {
			assertTrue(listBiddingData.contains(data));
		}

	}

	public List<BiddingData> createBiddingData() {
		List<BiddingData> biddingList = Arrays.asList(
				new BiddingData(Constants.ID, Constants.TRANSPORTER_ID, "load:1234", (long) 20,
						null, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null, null),
				new BiddingData("id1", Constants.TRANSPORTER_ID, Constants.LOAD_ID, (long) 20, null, BiddingData.Unit.PER_TON,
						Arrays.asList("truck:123"), false, true, null, null),
				new BiddingData("id2", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb61", Constants.LOAD_ID,
						(long) 40, null, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null, null),
				new BiddingData("id3", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb63", Constants.LOAD_ID, (long)20,
						null, BiddingData.Unit.PER_TON, Arrays.asList("truck:123", "truck:456"), false, true, null, null),
				new BiddingData("id4", "transporterId:12", Constants.LOAD_ID, (long) 20, null, BiddingData.Unit.PER_TON,
						Arrays.asList("truck:123"), false, true, null, null),
				new BiddingData("id5", "transporterId:12345", "load:1234", (long) 20, null, BiddingData.Unit.PER_TON,
						Arrays.asList("truck:123"), false, true, null, null)

		);

		return biddingList;
	}

}
