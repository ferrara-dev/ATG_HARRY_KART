package se.atg.service.harrykart.java.service;

import org.springframework.stereotype.Service;
import se.atg.service.harrykart.java.domain.*;
import se.atg.service.harrykart.java.domain.exception.ValidationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RankingService {
    private static final BigDecimal TRACK_LENGTH = BigDecimal.valueOf(1000);

    /**
     * This method ranks the horses by calculating their final race time.
     * If two participants have the same final race time, they are assigned the same ranking.
     * If the speed is less than or equals to zero, it means that the horse has stopped.
     * A horse stopping before the race is over is not expected and therefore an error is thrown.
     */
    public PlayHarryKartResponse computeRanking(HarryKartRequest request){
        validateRequestBody(request);
        int numberOfLoops = request.getNumberOfLoops();
        StartList startList = request.getStartList();
        PowerUps powerUps = request.getPowerUps();

        Map<Integer, BigDecimal> raceResults = calculateRaceResult(numberOfLoops, startList, powerUps);

        AtomicInteger position = new AtomicInteger(0);
        List<ParticipantRanking> rankings = new ArrayList<>();

        raceResults.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(participantEntry -> {

                    int ranking = -1;

                    for(ParticipantRanking r : rankings){
                        if(r.getFinalTime().equals(participantEntry.getValue())){
                            ranking = r.getPosition();
                            break;
                        }
                    }

                    if(ranking == -1)
                        ranking = position.incrementAndGet();

                    return ParticipantRanking.builder()
                            .position(ranking)
                            .horse(startList.getParticipant().stream()
                                    .filter(p -> p.getLane() == (participantEntry.getKey()))
                                    .findFirst().get().getName())
                            .finalTime(participantEntry.getValue())
                            .build();
                        })
                .forEachOrdered(participantRanking -> rankings.add(participantRanking));

        while (rankings.size() < 3)
            rankings.add(null);

        return PlayHarryKartResponse.builder()
                .ranking(rankings.subList(0,3))
                .build();
    }


    private Map<Integer, BigDecimal> calculateRaceResult(int numberOfLoops, StartList startList, PowerUps powerUps) {
        Map<Integer, BigDecimal> participantsMap = new HashMap<>();

        for (int lap = 0; lap < numberOfLoops; lap++) {
            List<Participant> participantList =  startList.getParticipant();
            for (Participant participant : participantList) {
                int speed = participant.getBaseSpeed();
                int lane = participant.getLane();
                    if (lap != 0) {
                        speed = speed + calculatePowerUp(powerUps,lap,lane);
                    }

                    if(speed <= 0){
                        throw new ValidationException("A horse unexpectedly stopped");
                    }

                    if(!participantsMap.containsKey(lane))
                        participantsMap.put(lane, BigDecimal.valueOf(0));

                    BigDecimal currentTime = participantsMap.get(lane);

                    BigDecimal elapsedTime = calculateElapsedTime(currentTime,speed);


                    participantsMap.put(lane,elapsedTime);
                }


        }


        return participantsMap;
    }

    private int calculatePowerUp(PowerUps powerUps, int lap, int lane){
       return powerUps.getLoop().get(lap - 1).getLane().get(lane - 1).getValue();
    }

    private BigDecimal calculateElapsedTime(BigDecimal currentTime, int speed) {
        BigDecimal elapsedTime = TRACK_LENGTH.divide(BigDecimal.valueOf(speed), 2, RoundingMode.CEILING);
        return currentTime.add(elapsedTime);
    }

    private void validateRequestBody(HarryKartRequest requestBody) {
        if (requestBody.getStartList().getParticipant().size() < 4)
            throw new ValidationException("Number of participants must be more than or equal to 4");
        requestBody.getPowerUps().getLoop().forEach(loop -> {
            if(loop.getLane().size() != requestBody.getStartList().getParticipant().size() ){
                throw new ValidationException("Number of power ups in each loop must be equal to number of participants");
            }
        });

    }

}
