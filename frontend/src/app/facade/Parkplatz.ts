import { Parkplatztyp } from "./Parkplatztyp"
import { Preiskategorie } from "./Preiskategorie"

export interface Parkplatz {
    parkplatzID: number
    nummer: number
    koordinate: string
    parkplatztyp: Parkplatztyp
    preiskategorie: Preiskategorie
}