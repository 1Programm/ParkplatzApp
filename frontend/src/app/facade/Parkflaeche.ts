import { AktivitaetsEnum } from "../utils/aktivitaetEnum.utils"
import { Parkplatz } from "./Parkplatz"

export interface Parkflaeche {
    parkflaecheID: number
    bezeichnung: string
    aktivitaet: AktivitaetsEnum
    parkplatzList: Parkplatz[]
}