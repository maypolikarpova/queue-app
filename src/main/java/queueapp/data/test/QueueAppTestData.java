package queueapp.data.test;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import queueapp.domain.appointment.Appointment;
import queueapp.domain.appointment.AppointmentStatus;
import queueapp.domain.queue.Queue;
import queueapp.domain.user.User;
import queueapp.repository.AppointmentRepository;
import queueapp.repository.QueueRepository;
import queueapp.repository.UserRepository;

import java.time.LocalDateTime;

@Component
@Profile("test_data")
public class QueueAppTestData {

    private  UserRepository userRepository;
    private  QueueRepository queueRepository;
    private  AppointmentRepository appointmentRepository;

    public QueueAppTestData(UserRepository userRepository, QueueRepository queueRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.queueRepository = queueRepository;
        this.appointmentRepository = appointmentRepository;

        addUsers();
        addQueues();
        addAppointments();
    }

    private void addUsers() {
        userRepository.save(createRuslan());
        userRepository.save(createMay());
        userRepository.save(createSofi());
    }
    private void addQueues() {
        queueRepository.save(ruslansQueue1());
        queueRepository.save(ruslansQueue2());
        queueRepository.save(sofisQueue());
    }

    private void addAppointments() {
        appointmentRepository.save(maysAppointmentToRuslansQueue1());
        appointmentRepository.save(sofisAppointmentToRuslansQueue1());

        appointmentRepository.save(maysAppointmentToRuslansQueue2());
        appointmentRepository.save(sofisAppointmentToRuslansQueue2());

        appointmentRepository.save(maysAppointmentToSofisQueue());
        appointmentRepository.save(ruslansAppointmentToSofisQueue());
    }

    private User createRuslan(){
        User ruslan = new User();
        ruslan.setName("Lan Rii");
        ruslan.setEmail("ruslanpurii@gmail.com");
        ruslan.setPassword("ruslan16");
        ruslan.setAddress("Київ, Україна");
        ruslan.setUserId("ruslan");
        ruslan.setPhoneNumber("380687117334");

        return ruslan;
    }

    private User createMay(){
        User may = new User();
        may.setName("May P");
        may.setEmail("maypolikarpova@gmail.com");
        may.setPassword("may123");
        may.setAddress("Київ, Україна");
        may.setPhoneNumber("380931237159");
        may.setUserId("may");

        return may;
    }

    private User createSofi(){
        User sofi = new User();
        sofi.setName("Sofia R");
        sofi.setEmail("sofiarylyuk@gmail.com");
        sofi.setPassword("sofi");
        sofi.setAddress("Київ, Україна");
        sofi.setPhoneNumber("380971237159");
        sofi.setUserId("sofi");

        return sofi;
    }

    private Queue ruslansQueue1() {
        Queue ruslansQueue1 = new Queue();
        ruslansQueue1.setName("Черга за зарахом");
        ruslansQueue1.setProviderId("ruslan");
        ruslansQueue1.setDescription("Приєднуйтесь до черги за зарахом з групового проекту!");
        ruslansQueue1.setAddress("2, вул Григорія Сковороди, Київ, 04655");
        ruslansQueue1.setPhoneNumber("380687117334");
        ruslansQueue1.setClosed(false);
        ruslansQueue1.setQueueId("ruslansQueue1");

        return ruslansQueue1;
    }

    private Queue ruslansQueue2() {
        Queue ruslansQueue2 = new Queue();
        ruslansQueue2.setName("Розмови про життя під вінішко");
        ruslansQueue2.setProviderId("ruslan");
        ruslansQueue2.setDescription("Вінішко, розмови про життя та музика на кухні у Майї");
        ruslansQueue2.setAddress("кв 143, вул Челябінська 19, Київ, Україна");
        ruslansQueue2.setPhoneNumber("380687117334");
        ruslansQueue2.setClosed(false);
        ruslansQueue2.setQueueId("ruslansQueue2");

        return ruslansQueue2;
    }

    private Queue sofisQueue() {
        Queue sofisQueue = new Queue();
        sofisQueue.setName("Вчу готувати, не дорого");
        sofisQueue.setProviderId("sofi");
        sofisQueue.setDescription("Навчу готувати за годину. Якщо не навчу - хоч нагодую");
        sofisQueue.setAddress("14Б, вул Марини Цвєтаєвої, Київ, Україна");
        sofisQueue.setPhoneNumber("380971237159");
        sofisQueue.setClosed(false);
        sofisQueue.setQueueId("sofisQueue");

        return sofisQueue;
    }

    private Appointment maysAppointmentToRuslansQueue1(){
        Appointment maysAppointment1 = new Appointment();
        maysAppointment1.setStatus(AppointmentStatus.APPROVED);
        maysAppointment1.setQueueId("ruslansQueue1");
        maysAppointment1.setAppointmentId("maysAppointment1");
        maysAppointment1.setClientId("may");
        maysAppointment1.setDateTimeFrom(LocalDateTime.parse("2018-12-12T10:00:00.000"));
        maysAppointment1.setDateTimeTo(LocalDateTime.parse("2018-12-12T10:10:00.000"));

        return maysAppointment1;
    }
    private Appointment sofisAppointmentToRuslansQueue1(){
        Appointment sofisAppointment1 = new Appointment();
        sofisAppointment1.setStatus(AppointmentStatus.APPROVED);
        sofisAppointment1.setQueueId("ruslansQueue1");
        sofisAppointment1.setAppointmentId("sofisAppointment1");
        sofisAppointment1.setClientId("sofi");
        sofisAppointment1.setDateTimeFrom(LocalDateTime.parse("2018-12-12T10:10:00.000"));
        sofisAppointment1.setDateTimeTo(LocalDateTime.parse("2018-12-12T10:20:00.000"));

        return sofisAppointment1;
    }

    private Appointment maysAppointmentToRuslansQueue2(){
        Appointment maysAppointment2 = new Appointment();
        maysAppointment2.setStatus(AppointmentStatus.REQUESTED);
        maysAppointment2.setQueueId("ruslansQueue2");
        maysAppointment2.setAppointmentId("maysAppointment2");
        maysAppointment2.setClientId("may");
        maysAppointment2.setDateTimeFrom(LocalDateTime.parse("2018-12-15T15:00:00.000"));
        maysAppointment2.setDateTimeTo(LocalDateTime.parse("2018-12-15T15:30:00.000"));

        return maysAppointment2;
    }
    private Appointment sofisAppointmentToRuslansQueue2(){
        Appointment sofisAppointment2 = new Appointment();
        sofisAppointment2.setStatus(AppointmentStatus.REQUESTED);
        sofisAppointment2.setQueueId("ruslansQueue2");
        sofisAppointment2.setAppointmentId("sofisAppointment2");
        sofisAppointment2.setClientId("sofi");
        sofisAppointment2.setDateTimeFrom(LocalDateTime.parse("2018-12-15T15:30:00.000"));
        sofisAppointment2.setDateTimeTo(LocalDateTime.parse("2018-12-15T16:00:00.000"));

        return sofisAppointment2;
    }

    private Appointment maysAppointmentToSofisQueue(){
        Appointment maysAppointment3 = new Appointment();
        maysAppointment3.setStatus(AppointmentStatus.REQUESTED);
        maysAppointment3.setQueueId("sofisQueue");
        maysAppointment3.setAppointmentId("maysAppointment3");
        maysAppointment3.setClientId("may");
        maysAppointment3.setDateTimeFrom(LocalDateTime.parse("2018-12-16T11:15:00.000"));
        maysAppointment3.setDateTimeTo(LocalDateTime.parse("2018-12-16T12:15:00.000"));

        return maysAppointment3;
    }
    private Appointment ruslansAppointmentToSofisQueue(){
        Appointment ruslansAppointment = new Appointment();
        ruslansAppointment.setStatus(AppointmentStatus.REQUESTED);
        ruslansAppointment.setQueueId("sofisQueue");
        ruslansAppointment.setAppointmentId("ruslansAppointment");
        ruslansAppointment.setClientId("ruslan");
        ruslansAppointment.setDateTimeFrom(LocalDateTime.parse("2018-12-16T12:15:00.000"));
        ruslansAppointment.setDateTimeTo(LocalDateTime.parse("2018-12-16T13:15:00.000"));

        return ruslansAppointment;
    }

}
