package com.mp.domain.party

import com.mp.domain.party.Address

class PartyAddress implements Serializable{
    Date fromDate
    Date thruDate
    Party party
    Address address

    static constraints = {
        thruDate(nullable:true)
        fromDate(nullable:false,blank:false)
    }
    static mapping ={
        id composite:['party','address']
    }

    static PartyAddress create(Party party, Address address, Date fromDate, Date thruDate=null, Boolean flush=false){
        PartyAddress partyAddress = new PartyAddress(party:party,address:address, fromDate:fromDate, thruDate:thruDate)
        partyAddress= partyAddress.save(flush:flush, insert:true)
        return partyAddress
    }

    static void remove(Party party, Address address, Boolean flush=false){
        PartyAddress partyAddress = PartyAddress.findByPartyAndAddress(party,address)
        partyAddress.delete(flush:flush)
    }

    static void removeAll(Party party){
        excecuteUpdate("Delete from PartyAddress where party=:party",[party:party])
    }

    static boolean hasAddress(Address address){
        return PartyAddress.countByAddress(address)>0
    }

}
