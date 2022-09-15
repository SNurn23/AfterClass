package Team4.egg.AfterClass.service;

import Team4.egg.AfterClass.entity.Meeting;
import Team4.egg.AfterClass.entity.Member;
import Team4.egg.AfterClass.entity.StudyGroup;
import Team4.egg.AfterClass.repository.MeetingRepository;
import Team4.egg.AfterClass.utility.ErrorService;
import Team4.egg.AfterClass.utility.StringsMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService implements StringsMethods {
    private final MeetingRepository meetingRepository;

    @Transactional
    public void createMeeting(StudyGroup group, Member member, Meeting meeting) throws ErrorService {
        meeting.setName(transformString(meeting.getName()));
        if(meeting.getName().isEmpty()){
            throw new ErrorService("Invalid Meeting Name");
        }
        meeting.setMember(member);
        meeting.setStudyGroup(group);
        meetingRepository.save(meeting);
    }

    @Transactional
    public void updateMeeting(Meeting meeting) throws ErrorService {
        Meeting meet = meetingRepository.findById(meeting.getId()).get();
        meeting.setName(transformString(meeting.getName()));
        if(meeting.getName().isEmpty()){
            throw new ErrorService("Invalid Meeting Name");
        }
        meet.setName(meeting.getName());
        meet.setDescription(meeting.getDescription());
        meet.setDate(meeting.getDate());
        meet.setScheduled(meeting.getScheduled());
        meet.setLink(meeting.getLink());
        meetingRepository.save(meet);
    }

    @Transactional(readOnly = true)
    public List<Meeting> getAll(){
        return meetingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Meeting> getAllByGroup(int id_group){
        return meetingRepository.findAllByIdGroup(id_group);
    }

    @Transactional(readOnly = true)//validaciones
    public Meeting getById(int id) {
        return meetingRepository.findById(id).get();
    }

    @Transactional
    public void deleteById(int id){//validaciones
        meetingRepository.deleteById(id);
    }
}
