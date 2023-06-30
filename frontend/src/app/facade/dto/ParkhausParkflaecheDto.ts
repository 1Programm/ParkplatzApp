export class ParkhausParkflaecheDto {
    bezeichnung: string;
    parkflaecheList: ParkflaecheDto[];
}

class ParkflaecheDto {
    parkflaecheID: number;
    bezeichnung: string;
}