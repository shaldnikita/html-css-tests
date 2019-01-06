package ru.shaldnikita.testing.web

import com.vaadin.flow.component.html.Div
import com.vaadin.flow.router.Route
import org.springframework.beans.factory.annotation.Autowired
import ru.shaldnikita.testing.web.mainscreen.MainScreen

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
@Route("")
class Router(@Autowired mainScreen: MainScreen) extends Div {
  setSizeFull()
  add(mainScreen)
}
