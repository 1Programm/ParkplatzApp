import { Parkplatz } from "../Parkplatz";

export class ParkplatzMitStatusDto{
    status: string | undefined;
    parkplatz: Parkplatz;

    
  static toParkplatzMitStatusDto(spot: Parkplatz): ParkplatzMitStatusDto {
    let newSpot: ParkplatzMitStatusDto = {
     parkplatz: spot,
     status: undefined
    };
    return newSpot;
    }
}