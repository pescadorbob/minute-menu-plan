package com.mp.analytics

class TestScenarioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [testScenarioList: TestScenario.list(params), testScenarioTotal: TestScenario.count()]
    }

    def create = {
        def testScenario = new TestScenario()
        testScenario.properties = params
        return [testScenario: testScenario]
    }

    def save = {
        def testScenario = new TestScenario(params)
        if (testScenario.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'testScenario.label', default: 'TestScenario'), testScenario.id])}"
            if(testScenario.active){
              def activeScenarios = TestScenario.findByActive(true)
              activeScenarios.each  {
                if(it.id!=testScenario.id){
                  it.active = false;
                  it.s()
                }
              }
              
              session.'active-scenario'=new TestScenarioCO(name:testScenario.name,id:testScenario.id,description:testScenario.description,notes:testScenario.notes,active:testScenario.active);
              request.setAttribute 'reset-scenario', true     
            }
            redirect(action: "show", id: testScenario.id)
        }
        else {
            render(view: "create", model: [testScenario: testScenario])
        }
    }

    def show = {
        def testScenario = TestScenario.get(params.id)
        if (!testScenario) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'testScenario.label', default: 'TestScenario'), params.id])}"
            redirect(action: "list")
        }
        else {
            [testScenario: testScenario]
        }
    }

    def edit = {
        def testScenario = TestScenario.get(params.id)
        if (!testScenario) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'testScenario.label', default: 'TestScenario'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [testScenario: testScenario]
        }
    }

    def update = {
        def testScenario = TestScenario.get(params.id)
        if (testScenario) {
            if (params.version) {
                def version = params.version.toLong()
                if (testScenario.version > version) {
                    
                    testScenario.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'testScenario.label', default: 'TestScenario')] as Object[], "Another user has updated this TestScenario while you were editing")
                    render(view: "edit", model: [testScenario: testScenario])
                    return
                }
            }
            testScenario.properties = params
            if (!testScenario.hasErrors() && testScenario.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'testScenario.label', default: 'TestScenario'), testScenario.id])}"
              if(testScenario.active){
                def activeScenarios = TestScenario.findByActive(true)
                activeScenarios.each {
                  if(it.id!=testScenario.id){
                    it.active = false;
                    it.s()
                  }
                }
                session.'active-scenario'=new TestScenarioCO(name:testScenario.name,id:testScenario.id,description:testScenario.description,notes:testScenario.notes,active:testScenario.active);                                
                request.setAttribute 'reset-scenario', true
              }
                redirect(action: "show", id: testScenario.id)
            }
            else {
                render(view: "edit", model: [testScenario: testScenario])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'testScenario.label', default: 'TestScenario'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def testScenario = TestScenario.get(params.id)
        if (testScenario) {
            try {
                testScenario.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'testScenario.label', default: 'TestScenario'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'testScenario.label', default: 'TestScenario'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'testScenario.label', default: 'TestScenario'), params.id])}"
            redirect(action: "list")
        }
    }
}
