import { AktivitaetsEnum } from "../utils/aktivitaetEnum.utils"
import { Parkplatztyp } from "./Parkplatztyp"
import { Preiskategorie } from "./Preiskategorie"

export interface Parkplatz {
    parkplatzID: number
    nummer: string
    xkoordinate: number
    ykoordinate: number
    aktivitaet: AktivitaetsEnum
    parkplatztyp: Parkplatztyp
    preiskategorie: Preiskategorie
}