package HailYoungHan.Board.repository.member;

import HailYoungHan.Board.dto.member.MemberDTO;

import java.util.List;

public interface MemberCustomRepository {

    MemberDTO getSingleMember(Long id);
    List<MemberDTO> getAllMembers();
}