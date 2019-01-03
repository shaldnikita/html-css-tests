package ru.shaldnikita.web.ui

import com.vaadin.flow.component.html.Div
import com.vaadin.flow.router.Route
import org.springframework.beans.factory.annotation.Autowired

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
@Route("")
class Router(@Autowired mainScreen: MainScreen) extends Div {
  add(mainScreen)
}
