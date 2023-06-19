import { Parkplatztyp } from "./Parkplatztyp"
import { Preiskategorie } from "./Preiskategorie"

export interface Parkplatz {
    parkplatzID: number
    nummer: String
    xkoordinate: number
    ykoordinate: number
    parkplatztyp: Parkplatztyp
    preiskategorie: Preiskategorie
}