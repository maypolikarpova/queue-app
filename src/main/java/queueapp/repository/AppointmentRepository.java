/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package queueapp.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import queueapp.domain.appointment.Appointment;
import queueapp.domain.appointment.AppointmentStatus;

import java.util.List;

@Repository
@Profile({"mongo", "fongo"})
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findByClientId(String clientId);
    List<Appointment> findByQueueId(String queueId);
    List<Appointment> findByQueueIdAndStatus(String queueId, AppointmentStatus status);
}
