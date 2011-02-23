package com.mp.wcm

import com.mp.domain.party.DirectorCoach
import com.mp.domain.party.CoachSubscriber
import com.mp.domain.Permission
import com.mp.domain.PartyRoleType
import org.hibernate.FetchMode
import com.mp.tools.UserTools
import org.weceem.html.WcmHTMLContent

public class WcmHelpTagLib {
  static namespace = "wcmhelp"
  def wcmContentRepositoryService

     def eachPage = { attrs,body ->
       def var = attrs.var ? attrs.var : "content"
         def pages  = wcmContentRepositoryService.findAllContent(wcmContentRepositoryService.findDefaultSpace())
         pages.each {
             if(it instanceof WcmHTMLContent && it.status.getPublicContent()){
               out << body((var): it)
             }
//     out << """${ConfigurationHolder.config.weceem.content.prefix}${content.absoluteURI}">${content.title}</a>"""

         }

     }
}
