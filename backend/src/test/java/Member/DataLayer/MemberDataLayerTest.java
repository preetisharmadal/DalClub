package Member.DataLayer;

import com.dal.cs.backend.member.Enum.MemberType;
import com.dal.cs.backend.member.MemberObject.Member;
import org.junit.jupiter.api.*;
import testUtils.BaseTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberDataLayerTest extends BaseTest {

    public MemberDataLayerTest() {
        super();
    }

    @AfterAll
    public void cleanUp() {
        cleanUpTest();
    }

    @Test
    public void createNewMemberTest() {
        Member randomMember = createMember(false, MemberType.member);
        Assertions.assertTrue(() -> iMemberDataLayer.createNewMember(randomMember));

        //Clean Up
        addToStack(Member.class, randomMember.getEmailId());
    }

    @Test
    public void getMemberTest() {
        Member randomMember = createMember(true, MemberType.member);
        Member recievedMember = iMemberDataLayer.getMember(randomMember.getEmailId());
        Assertions.assertNotNull(recievedMember, "Member not found");

        //Assert member values
        Assertions.assertEquals(randomMember.getEmailId(), recievedMember.getEmailId());
        Assertions.assertEquals(randomMember.getFirstName(), recievedMember.getFirstName());
        Assertions.assertEquals(randomMember.getLastName(), recievedMember.getLastName());
        Assertions.assertEquals(randomMember.getMemberType(), recievedMember.getMemberType());
        Assertions.assertEquals(randomMember.getProgram(), recievedMember.getProgram());
        Assertions.assertEquals(randomMember.getTerm(), recievedMember.getTerm());
        Assertions.assertEquals(randomMember.getMobile(), recievedMember.getMobile());
        Assertions.assertEquals(randomMember.getDob(), recievedMember.getDob());
    }

    @Test
    public void deleteMemberTest() {
        Member randomMember = createMember(true, MemberType.member);
        iMemberDataLayer.createNewMember(randomMember);
        Assertions.assertTrue(() -> iMemberDataLayer.deleteMember(randomMember.getEmailId()));
    }
}
