package ru.test.rest_example.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.test.rest_example.service.ClientService;
import ru.test.rest_example.service.model.Client;

import java.util.List;

@RestController
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Здесь мы обозначаем, что данный метод обрабатывает POST запросы на адрес /clients
     * С помощью него мы сможем в дальнейшем вернуть клиенту HTTP статус код.
     *
     * @RequestBody Client client значение этого параметра подставляется из тела запроса
     */
    @PostMapping(value = "/clients")
    public ResponseEntity<?> create(@RequestBody Client client) { //специальный класс для возврата ответов.
        clientService.create(client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @GetMapping(value = "/clients") — все аналогично аннотации @PostMapping,
     * только теперь мы обрабатываем GET запросы.
     * На этот раз мы возвращаем @ResponseEntity<List<Client>>,
     * только в этот раз, помимо HTTP статуса, мы вернем еще и тело ответа,
     * которым будет список клиентов.
     */
    @GetMapping(value = "/clients")
    public ResponseEntity<List<Client>> read() {
        final List<Client> clients = clientService.readAll();

        return clients != null && !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Данный метод будет принимать запросы на uri вида /clients/{id},
     * где вместо {id} может быть любое численное значение.
     * Данное значение, впоследствии,
     * передается переменной int id — параметру метода.
     */
    @GetMapping(value = "/clients/{id}")
    public ResponseEntity<Client> read(@PathVariable(name = "id") int id) {
        final Client client = clientService.read(id);

        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * метод update обрабатывает PUT запросы (аннотация @PutMapping)
     */
    @PutMapping(value = "/clients/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Client client) {
        final boolean updated = clientService.update(client, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

/**
 * метод delete обрабатывает DELETE запросы (аннотация DeleteMapping)*/
@DeleteMapping(value = "/clients/{id}")
    public  ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
    final boolean deleted = clientService.delete(id);

    return deleted
            ? new ResponseEntity<>(HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
