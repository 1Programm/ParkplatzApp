import { AktivitaetsEnum } from "../utils/aktivitaetEnum.utils"
import { Parkflaeche } from "./Parkflaeche"

export interface Parkhaus {
    parkhausID: number
    bezeichnung: string
    straße: string
    hausnummer: number
    plz: number
    ort: string
    aktivitaet: AktivitaetsEnum;
    parkflaecheList: Parkflaeche[]
}