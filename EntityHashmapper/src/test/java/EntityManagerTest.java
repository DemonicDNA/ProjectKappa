import com.google.inject.Guice;
import com.google.inject.Injector;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.UUID;

@Test
class EntityManagerTest {

    private Injector injector = Guice.createInjector(new HashmapperModule());
    private HashMapperProvider hashMapperProvider = injector.getInstance(HashMapperProvider.class);
    private EntityManager entityManager = injector.getInstance(EntityManager.class);

    @BeforeMethod
    void cleanEntityManager(){
        hashMapperProvider.getObjectHashMap().clear();
    }

    @Test
    void testPersist(){
        TestEntity testEntity = new TestEntity(1,1);
        entityManager.persist(testEntity);
       Assert.assertEquals(hashMapperProvider.getObjectHashMap().get(testEntity.getId()),testEntity);
    }

    @Test
    void testMergeNewEntity(){
        TestEntity testEntity = new TestEntity(1,2);
        Assert.assertNull(entityManager.merge(testEntity));
        Assert.assertEquals(hashMapperProvider.getObjectHashMap().get(testEntity.getId()),testEntity);
    }


    //note: the entity reference is saved in the hashmap
    //therefore if we set a field of an entity that is already in the hashmap we actually updating the hashmap's entity too
    //we can solve that using clone but then we will need to implement equals for every object to test if the object was really saved
    @Test
    void testMergeWithExisting(){
        Object testObject = new Object();
        TestEntity testEntity = new TestEntity(1,3);
        entityManager.persist(testEntity);
        UUID newObject = UUID.randomUUID();
        testEntity.setObject(newObject);
        Assert.assertEquals(entityManager.merge(testEntity).getObject(),testObject);
        Assert.assertEquals(((TestEntity)hashMapperProvider.getObjectHashMap().get(testEntity.getId())).getObject(),newObject);
    }

    @Test
    void testFind(){
        TestEntity testEntity = new TestEntity(1, 4);
        entityManager.persist(testEntity);
        Assert.assertEquals(entityManager.find(TestEntity.class,testEntity.getId()),testEntity);
    }


    private class TestEntity implements Entity<Long>{

        long id;
        Serializable object;

        public TestEntity(long id, Serializable object) {
            this.id = id;
            this.object = object;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Serializable object) {
            this.object = object;
        }

        @Override
        public Long getId() {
            return id;
        }

        /*@Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }*/

    }
}
