export class ParkhausParkflaecheDto {
    parkhausID: number;
    bezeichnung: string;
    parkflaecheList: ParkflaecheDto[];
}

export class ParkflaecheDto {
    parkflaecheID: number;
    bezeichnung: string;
    image: any;
}