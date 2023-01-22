package se.atg.service.harrykart.java.service;

import org.springframework.stereotype.Service;
import se.atg.service.harrykart.java.domain.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RankingService {
    private static final BigDecimal TRACK_LENGTH = BigDecimal.valueOf(1000);


    public PlayHarryKartResponse computeRanking(HarryKartRequest request){
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

                    return   ParticipantRanking.builder()
                            .position(position.incrementAndGet())
                            .horse(startList.getParticipant().stream()
                                    .filter(p -> p.getLane() == (participantEntry.getKey()))
                                    .findFirst().get().getName())
                            .finalTime(participantEntry.getValue())
                            .build();
                        })
                .forEachOrdered(participantRanking -> rankings.add(participantRanking));

        return PlayHarryKartResponse.builder()
                .ranking(rankings.subList(0,3))
                .build();
    }


    private Map<Integer, BigDecimal> calculateRaceResult(int numberOfLoops, StartList startList, PowerUps powerUps) {
        Map<Integer, BigDecimal> participantsMap = new HashMap<>();
        Set<Integer> disqualifiedLanes = new HashSet<>();

        for (int lap = 0; lap < numberOfLoops; lap++) {
            List<Participant> participantList =  startList.getParticipant();
            for (Participant participant : participantList) {
                int speed = participant.getBaseSpeed();
                int lane = participant.getLane();
                if(!disqualifiedLanes.contains(lane)){
                    if (lap != 0) {
                        speed = speed + calculatePowerUp(powerUps,lap,lane);
                    }
                    if(speed == 0){
                        disqualifiedLanes.add(lane);
                        participantsMap.remove(lane);
                        continue;
                    }

                    if(!participantsMap.containsKey(lane))
                        participantsMap.put(lane, BigDecimal.valueOf(0));

                    BigDecimal currentTime = participantsMap.get(lane);

                    BigDecimal elapsedTime = calculateElapsedTime(currentTime,speed);


                    participantsMap.put(lane,elapsedTime);
                }

            }
        }


        return participantsMap;
    }

    private int calculatePowerUp(PowerUps powerUps, int lap, int lane){
       return powerUps.getLoop().get(lap - 1).getLane().get(lane - 1).getValue();
    }

    private BigDecimal calculateElapsedTime(BigDecimal currentTime, int speed) {
        if(speed == 0){
    //        throw new HarryKartException("A horse unexpectedly has zero speed");
        }

        BigDecimal elapsedTime = TRACK_LENGTH.divide(BigDecimal.valueOf(speed), 2, RoundingMode.CEILING);
        return currentTime.add(elapsedTime);
    }

}
