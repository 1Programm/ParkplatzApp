import { Kennzeichen } from "./Kennzeichen"
import { Verstoss } from "./Verstoss";

export interface Mitarbeiter {
    
    mitarbeiterID: number;
    vorname: string;
    nachname: string;
    mail: string;
    kennzeichenList: Kennzeichen[];
    verstossList: Verstoss[];
};

