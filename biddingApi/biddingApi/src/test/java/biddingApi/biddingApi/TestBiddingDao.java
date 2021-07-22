package biddingApi.biddingApi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import biddingApi.biddingApi.Dao.BiddingDao;
import biddingApi.biddingApi.Entities.BiddingData;
import biddingApi.biddingApi.ErrorConstants.Constants;

@DataJpaTest
public class TestBiddingDao {

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

//	@Test
//	public void testFindByLoadId() {
//
//		Pageable currentPage;
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
//		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(1));
//		BiddingData savedInDb2 = entityManager.persist(listBiddingData.get(2));
//		BiddingData savedInDb3 = entityManager.persist(listBiddingData.get(3));
//		BiddingData savedInDb4 = entityManager.persist(listBiddingData.get(4));
//		BiddingData savedInDb5 = entityManager.persist(listBiddingData.get(5));
//
//		Iterable<BiddingData> allBids = biddingDao.findByLoadId(Constants.LOAD_ID);
//		List<BiddingData> list = new ArrayList<>();
//
//		for (BiddingData t : allBids) {
//			list.add(t);
//		}
//		assertThat(list.size()).isEqualTo(4);
//
//	}

//	@Test
//	public void testFindByLoadIdWithPagination() {
//
//		Pageable currentPage;
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
//		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(1));
//		BiddingData savedInDb2 = entityManager.persist(listBiddingData.get(2));
//		BiddingData savedInDb3 = entityManager.persist(listBiddingData.get(3));
//		BiddingData savedInDb4 = entityManager.persist(listBiddingData.get(4));
//		BiddingData savedInDb5 = entityManager.persist(listBiddingData.get(5));
//
//		currentPage = PageRequest.of(0, (int) Constants.pageSize);
//
//		Iterable<BiddingData> allBids = biddingDao.findByLoadId("load:123", currentPage);
//		List<BiddingData> list = new ArrayList<>();
//
//		for (BiddingData t : allBids) {
//			list.add(t);
//		}
//		assertThat(list.size()).isEqualTo(4);
//
//		Pageable nextPage = PageRequest.of(1, (int) Constants.pageSize);
//		Iterable<BiddingData> allBidsNextPage = biddingDao.findByLoadId("load:123", nextPage);
//
//		List<BiddingData> listNextPage = new ArrayList<>();
//
//		for (BiddingData t : allBidsNextPage) {
//			listNextPage.add(t);
//		}
//		assertThat(listNextPage.size()).isEqualTo(0);
//	}

//	@Test
//	public void testFindByTransporterIdWithPagination() {
//
//		Pageable currentPage;
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
//		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(1));
//		BiddingData savedInDb2 = entityManager.persist(listBiddingData.get(2));
//		BiddingData savedInDb3 = entityManager.persist(listBiddingData.get(3));
//		BiddingData savedInDb4 = entityManager.persist(listBiddingData.get(4));
//		BiddingData savedInDb5 = entityManager.persist(listBiddingData.get(5));
//
//		currentPage = PageRequest.of(0, (int) Constants.pageSize);
//		Iterable<BiddingData> allBids = biddingDao.findByTransporterId(Constants.TRANSPORTER_ID, currentPage);
//
//		List<BiddingData> list = new ArrayList<>();
//
//		for (BiddingData t : allBids) {
//			list.add(t);
//		}
//		assertThat(list.size()).isEqualTo(2);
//
//		Pageable nextPage = PageRequest.of(1, (int) Constants.pageSize);
//		Iterable<BiddingData> allBidsNextPage = biddingDao.findByTransporterId(Constants.TRANSPORTER_ID, nextPage);
//
//		List<BiddingData> listNextPage = new ArrayList<>();
//
//		for (BiddingData t : allBidsNextPage) {
//			listNextPage.add(t);
//		}
//		assertThat(listNextPage.size()).isEqualTo(0);
//
//	}

	@Test
	public void testFindByLoadIdAndTransporterIdWithPagination() {

		Pageable currentPage;
		List<BiddingData> listBiddingData = createBiddingData();

		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(1));
		BiddingData savedInDb2 = entityManager.persist(listBiddingData.get(2));

		currentPage = PageRequest.of(0, (int) Constants.pageSize);
		Iterable<BiddingData> allBids = biddingDao.findByLoadIdAndTransporterId(Constants.LOAD_ID,
				Constants.TRANSPORTER_ID, currentPage);

		List<BiddingData> list = new ArrayList<>();

		for (BiddingData t : allBids) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(1);

	}

	@Test
	public void testUpdate() {

		List<BiddingData> listBiddingData = createBiddingData();

		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));

		// entityManager.persist(truckData);
		Optional<BiddingData> getFromDb = biddingDao.findById(Constants.ID);

		listBiddingData.get(0).setCurrentBid((long) 100);

		entityManager.persist(listBiddingData.get(0));

		Iterable<BiddingData> allBids = biddingDao.findAll();

		List<BiddingData> list = new ArrayList<>();

		for (BiddingData t : allBids) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(1);

		assertThat(list.get(0).getCurrentBid()).isEqualTo(100);

	}

	@Test
	public void testDelete() {

		List<BiddingData> listBiddingData = createBiddingData();

		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(1));

		entityManager.remove(savedInDb1);

		Iterable<BiddingData> allBids = biddingDao.findAll();
		List<BiddingData> list = new ArrayList<>();

		for (BiddingData t : allBids) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(1);

	}

	public List<BiddingData> createBiddingData() {
		List<BiddingData> biddingList = Arrays.asList(
				new BiddingData(Constants.ID, Constants.TRANSPORTER_ID, "load:1234", (long) 20,
						null, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null),
				new BiddingData("id1", Constants.TRANSPORTER_ID, Constants.LOAD_ID, (long) 20, null, BiddingData.Unit.PER_TON,
						Arrays.asList("truck:123"), false, true, null),
				new BiddingData("id2", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb61", Constants.LOAD_ID,
						(long) 40, null, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null),
				new BiddingData("id3", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb63", Constants.LOAD_ID, null,
						null, BiddingData.Unit.PER_TON, Arrays.asList("truck:123", "truck:456"), false, true, null),
				new BiddingData("id4", "transporterId:12", Constants.LOAD_ID, (long) 20, null, BiddingData.Unit.PER_TON,
						Arrays.asList("truck:123"), false, true, null),
				new BiddingData("id5", "transporterId:12345", "load:1234", (long) 20, null, BiddingData.Unit.PER_TON,
						Arrays.asList("truck:123"), false, true, null)

		);

		return biddingList;
	}

}
