import { Kennzeichen } from "../Kennzeichen";
import { Parkplatz } from "../Parkplatz";

export class BuchungAbschlussDto {

    public parkplatzKennung: string;
    public datum: Date;
    public mitarbeiterID: number;
    public kennzeichen: Kennzeichen;
    public parkplatz: Parkplatz;

}