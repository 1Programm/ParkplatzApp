export class ParkhausParkflaecheDto {
    bezeichnung: string;
    parkflaecheList: ParkflaecheDto[];
}

export class ParkflaecheDto {
    parkflaecheID: number;
    bezeichnung: string;
}