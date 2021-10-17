package repasitoryTest;

//
//public class ItemJdbcRepoTest {
//    public class itemHibernateRepoTest {
//
//        @BeforeAll
//        public static void initDatabase() throws SQLException, ClassNotFoundException {
//            Connection databaseConnection = TestDatabaseConnection.initializeConnection();
//            Statement statement = databaseConnection.createStatement();
//            statement.executeUpdate("CREATE TABLE item (" +
//                    "id SERIAL PRIMARY KEY," +
//                    "name  VARCHAR," +
//                    "base_price  INTEGER" +
//                    ")");
//            databaseConnection.close();
//        }
//
//        @Test
//        public void testConnection() {
//            assertDoesNotThrow(TestDatabaseConnection::initializeConnection);
//        }
//
//        @Test
//        @DisplayName("Insert")
//        public void insert() throws SQLException, ClassNotFoundException {
//            ItemHibernateRepo itemHibernateRepo = new ItemHibernateRepo();
//            itemHibernateRepo.insert(new Stock("1","testI","img",200, Configuration.Resolution.HD));
//            Item item = itemHibernateRepo.findById("1");
//            assertNotNull(item);
//            assertEquals(1, item.getId());
//
//        }
//        @Test
//        @DisplayName("Delete")
//        public void delete() throws SQLException, ClassNotFoundException {
//            ItemHibernateRepo itemHibernateRepo = new ItemHibernateRepo();
//            itemHibernateRepo.deleteById(1L);
//            Optional<Item> item = itemHibernateRepo.findById(1L);
//            assertEquals(null, item.getId());
//
//        }
//        @Test
//        @DisplayName("Update")
//        public void update() throws SQLException, ClassNotFoundException {
//            ItemHibernateRepo itemHibernateRepo = new ItemHibernateRepo();
//            itemHibernateRepo.updateById(1L);
//            Optional<Item> item = itemHibernateRepo.findById(2L);
//            assertNotNull(item);
//            assertNotEquals("row2", item.getName());
//
//        }
//        @Test
//        @DisplayName("Search")
//        public void search() throws SQLException, ClassNotFoundException {
//            ItemHibernateRepo itemHibernateRepo = new ItemHibernateRepo();
//            List<Item> item = itemHibernateRepo.findItems(fail("1"));
//            assertNotNull(item);
//
//        }
//    }
//}
