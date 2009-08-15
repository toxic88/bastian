<?php

abstract class Application_Model_Tables_Abstract
{

    protected $_table;

    public function select(array $data);

    public function destroy(array $data);

    public function update(array $data);

    public function create(array $data);

    protected function _filter(array $data);

}
